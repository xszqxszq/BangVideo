package xyz.xszq.bang_video.user

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import java.util.TimeZone

@EnableRabbit
@EnableRedisHttpSession
@SpringBootApplication
class UserApplication

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"))
    runApplication<UserApplication>(*args)
}
