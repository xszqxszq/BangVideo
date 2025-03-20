package xyz.xszq.bang_video.video.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.video.dto.VideoDTO
import xyz.xszq.bang_video.video.service.VideoService
import xyz.xszq.bang_video.video.vo.VideoVO

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
    ): ResponseEntity<VideoVO?> {
        val userId = request.session.getAttribute("userId") ?.let {
            it as Long
        } ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        return videoService.create(dto, userId) ?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.badRequest().build()
    }
    @GetMapping("/{videoId}")
    fun info(
        @PathVariable
        videoId: Long,
        request: HttpServletRequest
    ): ResponseEntity<VideoVO?> {
        val userId = request.session.getAttribute("userId") ?.let {
            it as Long
        } ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        val video = kotlin.runCatching {
            videoService.profile(videoId, userId)
        }.onFailure {
            return ResponseEntity.status(when (it.message) {
                "NotFound" -> HttpStatus.NOT_FOUND
                "NotOwner" -> HttpStatus.FORBIDDEN
                "Deleted" -> HttpStatus.GONE
                else -> HttpStatus.INTERNAL_SERVER_ERROR
            }).body(null)
        }.getOrNull()
        return ResponseEntity.ok(video)
    }
    @PutMapping("/{videoId}")
    fun update(
        @PathVariable
        videoId: Long,
        @RequestBody
        dto: VideoDTO,
        request: HttpServletRequest
    ): ResponseEntity<VideoVO?> {
        val userId = request.session.getAttribute("userId") ?.let {
            it as Long
        } ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val video = kotlin.runCatching {
            videoService.update(videoId, dto, userId)
        }.onFailure {
            return ResponseEntity.status(when (it.message) {
                "NotFound" -> HttpStatus.NOT_FOUND
                "NotOwner" -> HttpStatus.FORBIDDEN
                else -> HttpStatus.INTERNAL_SERVER_ERROR
            }).body(null)
        }.getOrNull()
        return ResponseEntity.ok(video)
    }
    @DeleteMapping("/{videoId}")
    fun delete(
        @PathVariable
        videoId: Long,
        request: HttpServletRequest
    ): ResponseEntity<Boolean> {
        val userId = request.session.getAttribute("userId") ?.let {
            it as Long
        } ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false)

        val result = kotlin.runCatching {
            videoService.delete(videoId, userId)
        }.onFailure {
            return ResponseEntity.status(when (it.message) {
                "NotFound" -> HttpStatus.NOT_FOUND
                "NotOwner" -> HttpStatus.FORBIDDEN
                else -> HttpStatus.INTERNAL_SERVER_ERROR
            }).body(false)
        }.getOrNull()
        return ResponseEntity.ok(result)
    }
}