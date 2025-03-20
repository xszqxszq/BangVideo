package xyz.xszq.bang_video.user.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.common.vo.UserInternalVO
import xyz.xszq.bang_video.user.service.UserService

@RestController
@RequestMapping("/internal")
class UserInternalController(
    private val userService: UserService
) {
    @GetMapping("/get")
    fun get(
        userId: Long,
        password: String
    ): UserInternalVO? {
        return userService.authenticateSession(userId, password)
    }
}