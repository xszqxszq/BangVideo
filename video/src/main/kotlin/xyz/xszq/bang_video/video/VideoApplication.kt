package xyz.xszq.bang_video.video

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@EnableRabbit
@EnableRedisHttpSession
@EnableScheduling
@SpringBootApplication
class VideoApplication

fun main(args: Array<String>) {
    runApplication<VideoApplication>(*args)
}
