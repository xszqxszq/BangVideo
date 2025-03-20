package xyz.xszq.bang_video.community.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.common.action
import xyz.xszq.bang_video.common.withUser
import xyz.xszq.bang_video.community.VideoFeignService
import xyz.xszq.bang_video.community.dto.FavoriteCreateDTO
import xyz.xszq.bang_video.community.service.FavoriteService
import xyz.xszq.bang_video.community.vo.FavoriteVO

@RestController
@RequestMapping("/favorite")
class FavoriteController(
    private val service: FavoriteService,
    private val videoFeignService: VideoFeignService,
) {
    @GetMapping("/")
    fun listFavorites(
        request: HttpServletRequest
    ): ResponseEntity<List<FavoriteVO>?> =
        withUser(request) { userId ->
            service.list(userId)
        }
    @PostMapping("/")
    fun create(
        @RequestBody
        dto: FavoriteCreateDTO,
        request: HttpServletRequest
    ): ResponseEntity<FavoriteVO?> =
        withUser(request) { userId ->
            service.create(dto, userId)
        }
    @GetMapping("/{favoriteId}")
    fun info(
        @PathVariable
        favoriteId: String,
    ): ResponseEntity<FavoriteVO?> =
        action {
            service.info(favoriteId)
        }
    @DeleteMapping("/{favoriteId}")
    fun delete(
        @PathVariable
        favoriteId: String,
        request: HttpServletRequest
    ): ResponseEntity<Unit?> =
        withUser(request) { userId ->
            service.delete(favoriteId, userId)
        }
    @PostMapping("/{favoriteId}/{videoId}")
    fun addVideo(
        @PathVariable
        favoriteId: String,
        @PathVariable
        videoId: Long,
        request: HttpServletRequest
    ): ResponseEntity<Unit?> =
        videoFeignService.withUser(videoId, request) { userId ->
            service.addVideo(favoriteId, videoId, userId)
        }
    @DeleteMapping("/{favoriteId}/{videoId}")
    fun removeVideo(
        @PathVariable
        favoriteId: String,
        @PathVariable
        videoId: Long,
        request: HttpServletRequest
    ): ResponseEntity<Unit?> =
        withUser(request) { userId ->
            service.removeVideo(favoriteId, videoId, userId)
        }
}