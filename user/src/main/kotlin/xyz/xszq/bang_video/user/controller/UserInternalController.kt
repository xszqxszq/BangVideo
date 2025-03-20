package xyz.xszq.bang_video.user.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.user.service.UserService
import xyz.xszq.bang_video.user.vo.UserInternalVO

@RestController
@RequestMapping("/internal")
class UserInternalController(
    private val userService: UserService
) {
    @GetMapping("get")
    fun get(
        userId: Long,
        password: String,
        @RequestHeader headers: Map<String, String>
    ): UserInternalVO? {
        if (headers["x-request-source"] == "external") {
            return null
        }
        return userService.authenticateSession(userId, password)
    }
}