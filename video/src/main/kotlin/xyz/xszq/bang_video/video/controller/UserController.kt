package xyz.xszq.bang_video.video.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.common.vo.VideoVO
import xyz.xszq.bang_video.video.service.VideoService

@RestController
@RequestMapping("/user")
class UserController(
    private val service: VideoService
) {
    @GetMapping("/{userId}")
    fun list(
        @PathVariable userId: Long
    ): List<VideoVO> {
        return service.findByUser(userId)
    }
}