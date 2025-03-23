package xyz.xszq.bang_video.community

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.redis.util.RedisLockRegistry

@Configuration
@EnableIntegration
class RedisLockConfig {
    @Bean
    fun lockRegistry(factory: RedisConnectionFactory): RedisLockRegistry {
        return RedisLockRegistry(factory, "video-lock")
    }
}