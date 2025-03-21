package xyz.xszq.bang_video.encoding.controller

import com.rabbitmq.client.Channel
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import xyz.xszq.bang_video.common.dto.EncodingResult
import xyz.xszq.bang_video.encoding.ffmpeg.FFMpegFileType
import xyz.xszq.bang_video.encoding.ffmpeg.FFMpegTask
import xyz.xszq.bang_video.encoding.ffmpeg.FFProbe
import xyz.xszq.bang_video.encoding.service.ResolutionService
import kotlin.io.path.Path

@Component
class EncodingListener(
    private val service: ResolutionService,
    private val rabbitTemplate: RabbitTemplate,
) {
    private val video = Path("/static/video")
    private val tempDir = video.resolve("temp")
    @RabbitListener(queuesToDeclare = [Queue("encoding.queue")])
    fun handle(
        cid: Long,
        channel: Channel,
        message: Message
    ) {
        channel.basicAck(message.messageProperties.deliveryTag, false)
        val pre = tempDir.resolve(cid.toString()).toFile()
        val saveDir = video.resolve(cid.toString()).toFile().also {
            it.mkdir()
        }
        val videoStream = kotlin.runCatching {
            FFProbe(pre, showStreams = true).getResult().let { info ->
                val duration = info.format ?.duration ?.toDouble()
                    ?: throw IllegalStateException()
                if (duration < 1)
                    throw IllegalStateException()
                info.streams ?.firstOrNull { it.codecType == "video" }
            }
        }.onFailure {
            it.printStackTrace()
        }.getOrNull() ?: run {
            rabbitTemplate.convertAndSend("encoding.failed", cid)
            pre.delete()
            return
        }

        val width = videoStream.width ?: return
        val height = videoStream.height ?: return

        val resolutions = service.list().mapNotNull { resolution ->
            if (resolution.height > 480) {
                if (when {
                    width >= height -> resolution.height - height
                    else -> resolution.width - width
                } > 100)
                    return@mapNotNull null
            }
            val output = saveDir.resolve("${resolution.id}.mp4")
            FFMpegTask(FFMpegFileType.MP4) {
                input(pre.absolutePath)
                videoFilter(when {
                    width >= height -> "scale=-2:${resolution.height}"
                    else -> "scale=${resolution.width}:-2"
                })
                videoCodec("libx264")
                preset("ultrafast")
                yes()
            }.getResult(output)
            resolution.id
        }

        pre.delete()
        rabbitTemplate.convertAndSend("encoding.finished",
            EncodingResult(cid, resolutions)
        )
    }
}