package xyz.xszq.bang_video.video.controller

import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import xyz.xszq.bang_video.common.dto.EncodingResult
import xyz.xszq.bang_video.video.service.VideoSourceService

@Component
class EncodingListener(
    private val service: VideoSourceService
) {
    @RabbitListener(queuesToDeclare = [Queue("encoding.finished")])
    fun succeeded(
        result: EncodingResult
    ) {
        service.add(result.cid, result)
    }
    @RabbitListener(queuesToDeclare = [Queue("encoding.failed")])
    fun failed(
        cid: Long
    ) {
        service.add(cid, null)
    }
}