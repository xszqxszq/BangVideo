package xyz.xszq.bang_video.video.service

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class VideoIdGeneratorService (
    private val redisTemplate: StringRedisTemplate
) {
    companion object {
        private const val VIDEO_ID_KEY = "video:id"
    }
    fun generateVideoId(): Long {
        return redisTemplate.opsForValue()
            .increment(VIDEO_ID_KEY) ?: throw RuntimeException("Failed to generate video ID")
    }
}