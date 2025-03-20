package xyz.xszq.bang_video.video.service

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import xyz.xszq.bang_video.common.vo.VideoVO
import xyz.xszq.bang_video.video.dto.VideoDTO
import xyz.xszq.bang_video.video.entity.Video
import xyz.xszq.bang_video.video.mapper.VideoMapper
import xyz.xszq.bang_video.video.repository.VideoRepository
import java.time.LocalDateTime

@Service
class VideoService(
    private val videoRepository: VideoRepository,
    private val generator: VideoIdGeneratorService,
    private val mapper: VideoMapper,
    private val mongoTemplate: MongoTemplate
) {
    fun create(
        dto: VideoDTO,
        userId: Long
    ): VideoVO? {
        val videoId = generator.generateVideoId()
        val time = LocalDateTime.now()
        // TODO: get duration from video upload service
        val duration = 1
        val video = mapper.fromDTO(dto, videoId, userId, duration, time)
        videoRepository.save(video)
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
        // TODO: NEED TO GET FROM VIDEO UPLOAD SERVICE
        val duration = 1
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
    fun findByUser(userId: Long): List<VideoVO> {
        val videos = mongoTemplate.find(
            Query.query(Criteria
                .where("userId").`is`(userId)
                .and("deleted").`is`(false)
                .and("published").`is`(true)),
            Video::class.java
        ).mapNotNull { mapper.toVO(it) }
        return videos
    }
    fun batch(list: List<Long>): List<VideoVO> {
        return videoRepository.findAllByIdIn(list).mapNotNull { mapper.toVO(it) }
    }
}