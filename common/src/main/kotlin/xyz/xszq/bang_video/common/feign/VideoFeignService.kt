package xyz.xszq.bang_video.common.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import xyz.xszq.bang_video.common.vo.VideoVO

@FeignClient("video-service")
interface VideoFeignService {
    @GetMapping("/internal/info/{videoId}")
    fun getInfo(
        @PathVariable
        videoId: Long
    ): VideoVO?

    @PostMapping("/internal/batch")
    fun batch(
        @RequestBody
        list: List<Long>
    ): List<VideoVO>
}