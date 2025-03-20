package xyz.xszq.bang_video.video.controller

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
import xyz.xszq.bang_video.common.vo.VideoVO
import xyz.xszq.bang_video.common.withUser
import xyz.xszq.bang_video.video.dto.VideoDTO
import xyz.xszq.bang_video.video.service.VideoService

@RestController
@RequestMapping("/profile")
class ProfileController(
    private val videoService: VideoService
) {
    @PostMapping("/")
    fun create(
        @RequestBody
        dto: VideoDTO,
        request: HttpServletRequest
    ): ResponseEntity<VideoVO?> =
        withUser(request) { userId ->
            videoService.create(dto, userId)
        }
    @GetMapping("/{videoId}")
    fun info(
        @PathVariable
        videoId: Long,
        request: HttpServletRequest
    ): ResponseEntity<VideoVO?> =
        withUser(request) { userId ->
            videoService.profile(videoId, userId)
        }
    @PutMapping("/{videoId}")
    fun update(
        @PathVariable
        videoId: Long,
        @RequestBody
        dto: VideoDTO,
        request: HttpServletRequest
    ): ResponseEntity<VideoVO?> =
        withUser(request) { userId ->
            videoService.update(videoId, dto, userId)
        }
    @DeleteMapping("/{videoId}")
    fun delete(
        @PathVariable
        videoId: Long,
        request: HttpServletRequest
    ): ResponseEntity<Unit?> =
        withUser(request) { userId ->
            videoService.delete(videoId, userId)
        }
}