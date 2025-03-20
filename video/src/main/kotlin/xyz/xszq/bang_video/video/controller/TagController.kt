package xyz.xszq.bang_video.video.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.video.service.VideoService
import xyz.xszq.bang_video.common.vo.VideoVO

@RestController
@RequestMapping("/tag")
class TagController(
    private val service: VideoService
) {
    @GetMapping("/{tag}")
    fun list(@PathVariable tag: String): List<VideoVO> {
        return service.findByTag(tag)
    }
}