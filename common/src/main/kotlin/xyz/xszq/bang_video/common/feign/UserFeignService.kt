package xyz.xszq.bang_video.common.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import xyz.xszq.bang_video.common.vo.UserInternalVO

@FeignClient("user-service")
interface UserFeignService {
    @GetMapping("/internal/get")
    fun get(
        @RequestParam("userId")
        userId: Long,
        @RequestParam("password")
        password: String
    ): UserInternalVO?
}