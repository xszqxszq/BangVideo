package xyz.xszq.bang_video.user.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.user.service.UserService
import xyz.xszq.bang_video.user.vo.UserVO

@RestController
class QueryController(
    private val service: UserService
) {
    @GetMapping("/id/{userId}")
    fun infoById(
        @PathVariable
        userId: Long
    ): UserVO? {
        return service.getUserById(userId)
    }
    @GetMapping("/name/{name}")
    fun infoByName(
        @PathVariable
        name: String
    ): UserVO? {
        return service.getUserByNickname(name)
    }
}