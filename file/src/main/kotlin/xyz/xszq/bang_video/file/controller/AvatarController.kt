package xyz.xszq.bang_video.file.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import xyz.xszq.bang_video.file.vo.AvatarVO
import java.util.*
import javax.imageio.ImageIO
import kotlin.io.path.Path

@RestController
@RequestMapping("/avatar")
class AvatarController {
    private val avatar = "/static/avatar"
    private val uploadDir = Path(avatar)
    private val maxSize = 10485760
    @PostMapping("/")
    fun upload(
        @RequestParam("file")
        binary: MultipartFile
    ): ResponseEntity<AvatarVO?> {
        if (binary.bytes.size > maxSize)
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).build()
        if (ImageIO.read(binary.inputStream) == null)
            return ResponseEntity.badRequest().body(null)
        // TODO: Filter GIF
        val id = UUID.randomUUID().toString()
        val file = uploadDir.resolve(id).toFile()
        binary.transferTo(file)
        return ResponseEntity.ok(AvatarVO(id))
    }
}