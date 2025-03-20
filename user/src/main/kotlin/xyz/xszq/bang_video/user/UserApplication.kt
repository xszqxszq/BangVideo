package xyz.xszq.bang_video.user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@EnableRedisHttpSession
@SpringBootApplication
class UserApplication

fun main(args: Array<String>) {
    runApplication<UserApplication>(*args)
}
