package xyz.xszq.bang_video.community.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.common.withUser
import xyz.xszq.bang_video.community.VideoFeignService
import xyz.xszq.bang_video.community.dto.CommentDTO
import xyz.xszq.bang_video.community.service.CommentService
import xyz.xszq.bang_video.community.vo.CommentInfoVO
import xyz.xszq.bang_video.community.vo.CommentVO

@RestController
@RequestMapping("/comment")
class CommentController(
    private val service: CommentService,
    private val videoFeignService: VideoFeignService
) {
    @GetMapping("/{videoId}")
    fun list(@PathVariable videoId: Long): List<CommentVO> {
        return service.list(videoId)
    }
    @PostMapping("/{videoId}")
    fun create(
        @PathVariable videoId: Long,
        @RequestBody dto: CommentDTO,
        request: HttpServletRequest
    ): ResponseEntity<CommentInfoVO?> =
        videoFeignService.withUser(videoId, request) { userId ->
            service.create(videoId, userId, dto)
        }
    @PutMapping("/{commentId}")
    fun update(
        @PathVariable commentId: String,
        @RequestBody dto: CommentDTO,
        request: HttpServletRequest
    ): ResponseEntity<CommentInfoVO?> =
        withUser(request) { userId ->
            service.update(commentId, userId, dto)
        }
    @DeleteMapping("/{commentId}")
    fun delete(
        @PathVariable commentId: String,
        request: HttpServletRequest
    ): ResponseEntity<Unit?> =
        withUser(request) { userId ->
            service.delete(commentId, userId)
        }
}