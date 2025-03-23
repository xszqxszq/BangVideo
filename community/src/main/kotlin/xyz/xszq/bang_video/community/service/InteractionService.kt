package xyz.xszq.bang_video.community.service

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.integration.support.locks.LockRegistry
import org.springframework.stereotype.Service

@Service
class InteractionService(
    private val redisTemplate: StringRedisTemplate,
    private val lockRegistry: LockRegistry
) {
    val shardLength = 1024
    fun view(videoId: Long, key: String) {
        redisTemplate.opsForSet().add("video:views:$videoId", key)
    }
    fun like(videoId: Long, userId: Long) {
        redisTemplate.opsForSet().add("video:likes:$videoId", userId.toString())
    }
    fun dislike(videoId: Long, userId: Long) {
        redisTemplate.opsForSet().remove("video:likes:$videoId", userId.toString())
    }
    fun getVideos(): Set<String> {
        val videoIds = mutableSetOf<String>()
        val scanOptions = ScanOptions.scanOptions().count(shardLength.toLong()).build()
        val cursor = redisTemplate.opsForSet().scan("video:ids", scanOptions)
        while (cursor.hasNext()) {
            videoIds.add(cursor.next())
        }
        return videoIds
    }
    fun countViews(rabbit: RabbitTemplate) {
        val lock = lockRegistry.obtain("video-views-lock").also {
            if (!it.tryLock())
                return
        }
        getVideos().mapNotNull { videoIdStr ->
            val videoId = videoIdStr.toLong()
            val redisKey = "video:views:$videoId"
            val count = redisTemplate.opsForSet().size(redisKey) ?: 0

            if (count > 0) {
                Pair(videoId, count)
            } else {
                null
            }
        }.chunked(shardLength).forEach { videos ->
            rabbit.convertAndSend("video.views", videos)

            redisTemplate.delete(videos.map { "video:views:${it.first}" })
        }
        lock.unlock()
    }
    fun countLikes(rabbit: RabbitTemplate) {
        val lock = lockRegistry.obtain("video-likes-lock").also {
            if (!it.tryLock())
                return
        }
        getVideos().mapNotNull { videoIdStr ->
            val videoId = videoIdStr.toLong()
            val redisKey = "video:likes:$videoId"
            val count = redisTemplate.opsForSet().size(redisKey) ?: 0

            if (count > 0) {
                Pair(videoId, count)
            } else {
                null
            }
        }.chunked(shardLength).forEach { videos ->
            rabbit.convertAndSend("video.likes", videos)
        }
        lock.unlock()
    }
}