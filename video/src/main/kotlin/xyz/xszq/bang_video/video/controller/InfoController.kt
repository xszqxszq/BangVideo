package xyz.xszq.bang_video.video.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.common.action
import xyz.xszq.bang_video.common.vo.VideoVO
import xyz.xszq.bang_video.video.service.VideoService

@RestController
@RequestMapping("/info")
class InfoController(
    private val service: VideoService
) {
    @GetMapping("/{videoId}")
    fun getInfo(@PathVariable videoId: Long): ResponseEntity<VideoVO?> =
        action {
            service.findById(videoId)
        }
}