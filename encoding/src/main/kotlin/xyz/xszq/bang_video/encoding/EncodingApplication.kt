package xyz.xszq.bang_video.encoding

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File

@EnableRabbit
@SpringBootApplication
class EncodingApplication

fun main(args: Array<String>) {
    val temp = File("/static/video/temp")
    if (!temp.exists())
        temp.mkdirs()
    runApplication<EncodingApplication>(*args)
}
