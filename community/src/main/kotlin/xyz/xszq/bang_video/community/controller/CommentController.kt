package xyz.xszq.bang_video.community.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.community.service.CommentService
import xyz.xszq.bang_video.community.vo.CommentVO

@RestController
@RequestMapping("/comment")
class CommentController(
    private val service: CommentService
) {
    @GetMapping("/{videoId}")
    fun list(@PathVariable videoId: Long): List<CommentVO> {
        return service.list(videoId)
    }
}