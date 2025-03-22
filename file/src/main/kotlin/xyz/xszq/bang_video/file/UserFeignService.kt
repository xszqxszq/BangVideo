package xyz.xszq.bang_video.file

import org.springframework.cloud.openfeign.FeignClient
import xyz.xszq.bang_video.common.feign.UserFeignService

@FeignClient("user-service")
interface UserFeignService: UserFeignService