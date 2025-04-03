package xyz.xszq.bang_video.community.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.common.vo.VideoVO
import xyz.xszq.bang_video.common.withUser
import xyz.xszq.bang_video.community.service.NotificationService

@RestController
@RequestMapping("/notify")
class NotificationController(
    private val service: NotificationService
) {
    @GetMapping("/unread")
    fun unread(
        request: HttpServletRequest
    ) = withUser(request) { userId ->
        service.unread(userId)
    }
    @GetMapping("/")
    fun list(
        request: HttpServletRequest
    ) = withUser(request) { userId ->
        service.list(userId)
    }
    @RabbitListener(queuesToDeclare = [Queue("notify.audit")])
    fun updateAudit(video: VideoVO) {
        service.notifyAudit(video)
    }
}