package xyz.xszq.bang_video.community

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import java.util.TimeZone

@EnableRabbit
@EnableScheduling
@EnableRedisHttpSession
@SpringBootApplication
class CommunityApplication

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"))
    runApplication<CommunityApplication>(*args)
}
