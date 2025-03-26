package xyz.xszq.bang_video.file.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import xyz.xszq.bang_video.file.vo.ImageVO
import java.nio.file.Path
import java.util.*
import javax.imageio.ImageIO
import kotlin.io.path.Path

@RestController
@RequestMapping("/")
class ImageController {
    private val avatar = "/static/avatar"
    private val avatarDir = Path(avatar)
    private val cover = "/static/cover"
    private val coverDir = Path(cover)
    private val maxSize = 10485760
    @PostMapping("/avatar/")
    fun avatar(
        @RequestParam("file")
        binary: MultipartFile
    ): ResponseEntity<ImageVO?> {
        return upload(binary, avatarDir)
    }
    @PostMapping("/cover/")
    fun cover(
        @RequestParam("file")
        binary: MultipartFile
    ): ResponseEntity<ImageVO?> {
        return upload(binary, coverDir)
    }
    fun upload(binary: MultipartFile, dir: Path): ResponseEntity<ImageVO?> {
        if (binary.bytes.size > maxSize)
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).build()
        if (ImageIO.read(binary.inputStream) == null)
            return ResponseEntity.badRequest().body(null)
        val id = UUID.randomUUID().toString()
        val file = dir.resolve(id).toFile()
        binary.transferTo(file)
        return ResponseEntity.ok(ImageVO(id))
    }
}