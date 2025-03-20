package xyz.xszq.bang_video.video

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@EnableFeignClients
@EnableRedisHttpSession
@SpringBootApplication
class VideoApplication

fun main(args: Array<String>) {
    runApplication<VideoApplication>(*args)
}
