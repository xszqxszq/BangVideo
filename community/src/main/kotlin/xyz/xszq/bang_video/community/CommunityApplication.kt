package xyz.xszq.bang_video.community

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@EnableFeignClients
@EnableRedisHttpSession
@SpringBootApplication
class CommunityApplication

fun main(args: Array<String>) {
    runApplication<CommunityApplication>(*args)
}
