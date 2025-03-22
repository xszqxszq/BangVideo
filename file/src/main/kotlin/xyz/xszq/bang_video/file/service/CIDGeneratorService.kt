package xyz.xszq.bang_video.file.service

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class CIDGeneratorService (
    private val redisTemplate: StringRedisTemplate
) {
    companion object {
        private const val VIDEO_ID_KEY = "video_cid:id"
    }
    fun generateCID(): Long {
        return redisTemplate.opsForValue()
            .increment(VIDEO_ID_KEY) ?: throw RuntimeException("Failed to generate video CID")
    }
}