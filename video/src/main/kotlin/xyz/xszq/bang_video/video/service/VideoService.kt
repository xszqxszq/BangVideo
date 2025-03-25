package xyz.xszq.bang_video.video.service

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.integration.support.locks.LockRegistry
import org.springframework.stereotype.Service
import xyz.xszq.bang_video.common.toTime
import xyz.xszq.bang_video.common.vo.VideoVO
import xyz.xszq.bang_video.video.dto.AuditDTO
import xyz.xszq.bang_video.video.dto.VideoDTO
import xyz.xszq.bang_video.video.entity.Video
import xyz.xszq.bang_video.video.entity.VideoSearch
import xyz.xszq.bang_video.video.mapper.VideoMapper
import xyz.xszq.bang_video.video.repository.VideoRepository
import xyz.xszq.bang_video.video.repository.VideoSearchRepository
import xyz.xszq.bang_video.video.repository.VideoSourceRepository
import java.time.LocalDateTime
import kotlin.math.roundToInt

@Service
class VideoService(
    private val videoRepository: VideoRepository,
    private val sourceRepository: VideoSourceRepository,
    private val searchRepository: VideoSearchRepository,
    private val generator: VideoIdGeneratorService,
    private val mapper: VideoMapper,
    private val mongoTemplate: MongoTemplate,
    private val redisTemplate: StringRedisTemplate,
    private val lockRegistry: LockRegistry
) {
    fun create(
        dto: VideoDTO,
        userId: Long
    ): VideoVO? {
        val videoId = generator.generateVideoId()
        val time = LocalDateTime.now()
        val source = sourceRepository.findByIdOrNull(dto.cid)
            ?: throw Exception("NotFound")
        val duration = source.duration
        val video = mapper.fromDTO(dto, videoId, userId, duration, time)

        videoRepository.save(video)
        redisTemplate.opsForSet().add("video:ids", videoId.toString())
        searchRepository.save(
            VideoSearch(
                id = videoId,
                title = video.title,
                description = video.description,
                tags = video.tags,
            )
        )

        return mapper.toVO(video)
    }
    fun profile(
        id: Long,
        userId: Long
    ): VideoVO? {
        val video = videoRepository.findByIdOrNull(id) ?: throw Exception("NotFound")
        if (video.owner != userId)
            throw Exception("NotOwner")
        if (video.deleted)
            throw Exception("Deleted")
        return mapper.toVO(video)
    }
    fun update(
        id: Long,
        dto: VideoDTO,
        userId: Long
    ): VideoVO? {
        val video = videoRepository.findByIdOrNull(id) ?: throw Exception("NotFound")
        if (video.owner != userId)
            throw Exception("NotOwner")

        mapper.update(dto, video)
        val source = sourceRepository.findByIdAndSucceededTrue(dto.cid)
            ?: throw Exception("NotFound")
        val duration = source.duration
        video.duration = duration
        video.updated = LocalDateTime.now()

        videoRepository.save(video)
        return mapper.toVO(video)
    }
    fun delete(
        id: Long,
        userId: Long
    ) {
        val video = videoRepository.findByIdOrNull(id) ?: throw Exception("NotFound")
        if (video.owner != userId)
            throw Exception("NotOwner")
        if (video.deleted)
            throw Exception("NotFound")

        video.deleted = true
        videoRepository.save(video)
        // TODO: set deleted
        searchRepository.deleteById(id)
    }
    fun findById(id: Long): VideoVO? {
        return videoRepository.findByIdOrNull(id)?.let {
            if (it.deleted)
                throw Exception("Deleted")
            if (!it.published)
                throw Exception("NotPublished")
            mapper.toVO(it)
        }
    }
    fun findByTag(tag: String): List<VideoVO> {
        val videos = mongoTemplate.find(
            Query.query(Criteria
                .where("tags").`is`(tag)
                .and("deleted").`is`(false)
                .and("published").`is`(true)),
            Video::class.java
        ).mapNotNull { mapper.toVO(it) }
        return videos
    }
    fun findByCategory(category: Int): List<VideoVO> {
        val videos = mongoTemplate.find(
            Query.query(Criteria
                .where("category").`is`(category)
                .and("deleted").`is`(false)
                .and("published").`is`(true)),
            Video::class.java
        ).mapNotNull { mapper.toVO(it) }
        return videos
    }
    fun findByUser(userId: Long, requirePublished: Boolean = true): List<VideoVO> {
        val videos = mongoTemplate.find(
            Query.query(Criteria
                .where("userId").`is`(userId)
                .and("deleted").`is`(false)
                .let {
                    if (requirePublished)
                        it.and("published").`is`(true)
                    else
                        it
                }),
            Video::class.java
        ).mapNotNull { mapper.toVO(it) }
        return videos
    }
    fun search(keyword: String): List<VideoVO> {
        QueryBuilders
            .multiMatch()
            .query(keyword)
            .fields("title", "description", "tags")
            .build()
        return videoRepository
            .findAllByIdIn(searchRepository.search(keyword).map { it.id })
            .mapNotNull { mapper.toVO(it) }
    }
    fun batch(list: List<Long>): List<VideoVO> {
        return videoRepository.findAllByIdIn(list).mapNotNull { mapper.toVO(it) }
    }
    fun updateViews(list: List<Pair<Long, Long>>) = list.forEach { (id, views) ->
        videoRepository.findByIdOrNull(id) ?.let { video ->
            video.views += views
            videoRepository.save(video)
        }
    }
    fun updateLikes(list: List<Pair<Long, Long>>) = list.forEach { (id, likes) ->
        videoRepository.findByIdOrNull(id) ?.let { video ->
            video.likes = likes
            videoRepository.save(video)
        }
    }
    fun updateAuditCID(audit: AuditDTO) {
        val source = sourceRepository.findByIdAndSucceededTrue(audit.cid) ?: return
        if (audit.pass) {
            source.auditPassed = true
            redisTemplate.opsForSet().add("video:audit:passed", audit.cid.toString())
        } else {
            source.auditMessage = buildString {
                append(audit.frames.joinToString("、") { frame ->
                    (frame / source.fps).roundToInt().toTime()
                })
                append("处有不适宜内容，请重新修改")
            }
            redisTemplate.opsForSet().add("video:audit:failed", audit.cid.toString())
        }
        sourceRepository.save(source)
    }
    fun updateAuditVideo() {
        // TODO: Prevent multiple instances
        val lock = lockRegistry.obtain("video-audit-lock")
        if (!lock.tryLock())
            return
        kotlin.runCatching {
            redisTemplate.opsForSet().members("video:audit:passed") ?.let { members ->
                videoRepository.findAllByCidIn(members.toList().map { it.toLong() }).forEach { video ->
                    video.published = true
                    video.auditPassed = true
                    videoRepository.save(video)
                    redisTemplate.opsForSet().remove("video:audit:passed", video.cid.toString())
                }
            }
            redisTemplate.opsForSet().members("video:audit:failed") ?.let { members ->
                videoRepository.findAllByCidIn(members.toList().map { it.toLong() }).forEach { video ->
                    val source = sourceRepository.findByIdAndSucceededTrue(video.cid) ?: return@forEach
                    video.auditPassed = false
                    video.auditMessage = source.auditMessage
                    videoRepository.save(video)
                    redisTemplate.opsForSet().remove("video:audit:failed", video.cid.toString())
                }
            }
        }.onFailure {
            it.printStackTrace()
        }
        lock.unlock()
    }
}