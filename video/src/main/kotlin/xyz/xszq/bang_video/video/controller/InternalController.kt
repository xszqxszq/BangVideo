package xyz.xszq.bang_video.video.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.common.vo.VideoVO
import xyz.xszq.bang_video.video.service.VideoService

@RestController
@RequestMapping("/internal")
class InternalController(
    private val service: VideoService
) {
    @GetMapping("/info/{videoId}")
    fun single(
        @PathVariable
        videoId: Long
    ): VideoVO? {
        return service.findById(videoId)
    }
    @PostMapping("/batch")
    fun batch(
        @RequestBody
        list: List<Long>
    ): List<VideoVO> {
        return service.batch(list)
    }
}