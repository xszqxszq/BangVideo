package xyz.xszq.bang_video.file.controller

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import xyz.xszq.bang_video.file.service.CIDGeneratorService
import xyz.xszq.bang_video.file.vo.VideoVO
import kotlin.io.path.Path

@RestController
@RequestMapping("/video")
class VideoController(
    private val generator: CIDGeneratorService,
    private val rabbitTemplate: RabbitTemplate,
) {
    private val video = "/static/video/temp"
    private val uploadDir = Path(video)
    @PostMapping("/")
    fun upload(
        @RequestParam("file")
        binary: MultipartFile
    ): ResponseEntity<VideoVO?> {
        val cid = generator.generateCID()
        val file = uploadDir.resolve(cid.toString()).toFile()
        binary.transferTo(file)
        rabbitTemplate.convertAndSend("encoding.queue", cid)

        return ResponseEntity.ok(VideoVO(cid))
    }
}