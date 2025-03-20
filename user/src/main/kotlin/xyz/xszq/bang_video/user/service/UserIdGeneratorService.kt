package xyz.xszq.bang_video.user.service

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class UserIdGeneratorService (
    private val redisTemplate: StringRedisTemplate
) {
    companion object {
        private const val USER_ID_KEY = "user:id"
    }
    fun generateUserId(): Long {
        return redisTemplate.opsForValue()
            .increment(USER_ID_KEY) ?: throw RuntimeException("Failed to generate user ID")
    }
}