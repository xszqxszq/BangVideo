package xyz.xszq.bang_video.file

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import java.io.File
import java.util.TimeZone

@EnableRedisHttpSession
@SpringBootApplication
class FileApplication

fun main(args: Array<String>) {
	val avatar = File("/static/avatar")
	if (!avatar.exists())
		avatar.mkdir()
	val cover = File("/static/cover")
	if (!cover.exists())
		cover.mkdir()
	val video = File("/static/video/temp")
	if (!video.exists())
		video.mkdirs()
	TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"))
	runApplication<FileApplication>(*args)
}
