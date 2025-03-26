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
    @RabbitListener(queuesToDeclare = [Queue("video.encoding.finished")])
    fun succeeded(
        result: EncodingResult
    ) {
        println("Encode ${result.cid} succeeded.")
        service.add(result.cid, result)
    }
    @RabbitListener(queuesToDeclare = [Queue("video.encoding.failed")])
    fun failed(
        cid: Long
    ) {
        println("Encode $cid failed.")
        service.add(cid, null)
    }
}