package xyz.xszq.bang_video.encoding

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableRabbit
@SpringBootApplication
class EncodingApplication

fun main(args: Array<String>) {
    runApplication<EncodingApplication>(*args)
}
