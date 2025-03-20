package xyz.xszq.bang_video.community

import org.springframework.cloud.openfeign.FeignClient
import xyz.xszq.bang_video.common.feign.VideoFeignService

@FeignClient("video-service")
interface VideoFeignService: VideoFeignService