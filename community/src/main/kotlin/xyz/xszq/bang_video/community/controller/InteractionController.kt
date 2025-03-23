package xyz.xszq.bang_video.community.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.common.getIP
import xyz.xszq.bang_video.common.withUser
import xyz.xszq.bang_video.common.withVideo
import xyz.xszq.bang_video.community.service.InteractionService

@RestController
class InteractionController(
    private val rabbit: RabbitTemplate,
    private val service: InteractionService,
) {
    @PostMapping("/view/{videoId}")
    fun view(
        @PathVariable
        videoId: Long,
        request: HttpServletRequest,
    ): ResponseEntity<Unit?> =
        rabbit.withVideo(videoId) {
            val userId = request.session.getAttribute("userId") as Long?
            val userKey = userId?.toString()
                ?: (getIP(request) + ":" + request.getHeader("User-Agent"))
            service.view(videoId, userKey)
            return ResponseEntity.ok().build()
        }
    @PostMapping("/like/{videoId}")
    fun like(
        @PathVariable
        videoId: Long,
        request: HttpServletRequest,
    ): ResponseEntity<Unit?> =
        rabbit.withUser(videoId, request) { userId ->
            service.like(videoId, userId)
            return ResponseEntity.ok().build()
        }
    @DeleteMapping("/like/{videoId}")
    fun dislike(
        @PathVariable
        videoId: Long,
        request: HttpServletRequest,
    ): ResponseEntity<Unit?> =
        rabbit.withUser(videoId, request) { userId ->
            service.dislike(videoId, userId)
            return ResponseEntity.ok().build()
        }

    // TODO: Ensure only one instance executes this operation
    @Scheduled(cron = "0 * * * * *")
    fun countViews() {
        service.countViews(rabbit)
    }
    // TODO: Ensure only one instance executes this operation
    @Scheduled(cron = "0 * * * * *")
    fun countLikes() {
        service.countLikes(rabbit)
    }
}