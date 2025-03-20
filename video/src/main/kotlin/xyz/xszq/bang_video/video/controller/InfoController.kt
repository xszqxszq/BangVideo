package xyz.xszq.bang_video.video.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.video.service.VideoService
import xyz.xszq.bang_video.video.vo.VideoVO

@RestController
@RequestMapping("/info")
class InfoController(
    private val service: VideoService
) {
    @GetMapping("/{videoId}")
    fun getInfo(@PathVariable videoId: Long): ResponseEntity<VideoVO?> {
        val video = kotlin.runCatching {
            service.findById(videoId)
        }.onFailure {
            return ResponseEntity.status(when(it.message) {
                "Deleted" -> HttpStatus.GONE
                "NotPublished" -> HttpStatus.FORBIDDEN
                else -> HttpStatus.INTERNAL_SERVER_ERROR
            }).body(null)
        }.getOrNull()
        return video ?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }
}