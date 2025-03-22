package xyz.xszq.bang_video.video.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.common.action
import xyz.xszq.bang_video.video.service.VideoSourceService
import xyz.xszq.bang_video.video.vo.VideoSourceVO

@RestController
@RequestMapping("/source")
class SourceController(
    private val service: VideoSourceService
) {
    @GetMapping("/{cid}")
    fun get(
        @PathVariable("cid")
        cid: Long,
    ): ResponseEntity<VideoSourceVO?> =
        action {
            service.get(cid)
        }
}