package xyz.xszq.bang_video.video.controller

import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import xyz.xszq.bang_video.common.vo.VideoVO
import xyz.xszq.bang_video.video.dto.AuditDTO
import xyz.xszq.bang_video.video.service.VideoService

@Component
class VideoListener(
    private val service: VideoService
) {
    @RabbitListener(queuesToDeclare = [Queue("video.info")])
    fun batchInfo(ids: List<Long>): List<VideoVO> {
        return service.batch(ids)
    }
    @RabbitListener(queuesToDeclare = [Queue("video.views")])
    fun updateViews(views: List<Pair<Long, Long>>) {
        service.updateViews(views)
    }
    @RabbitListener(queuesToDeclare = [Queue("video.likes")])
    fun updateLikes(likes: List<Pair<Long, Long>>) {
        service.updateLikes(likes)
    }
    @RabbitListener(queuesToDeclare = [Queue("video.audit.finished")])
    fun updateAudit(audit: AuditDTO) {
        service.updateAuditCID(audit)
    }
}