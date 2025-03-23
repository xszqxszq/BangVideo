package xyz.xszq.bang_video.user.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import xyz.xszq.bang_video.common.withUser
import xyz.xszq.bang_video.user.dto.UserUpdateDTO
import xyz.xszq.bang_video.user.service.UserService
import xyz.xszq.bang_video.user.vo.UserDetailedVO

@RestController
@RequestMapping("/profile")
class ProfileController(
    private val service: UserService
) {
    @GetMapping("/")
    fun query(
        request: HttpServletRequest
    ): ResponseEntity<UserDetailedVO?> =
        withUser(request) { userId ->
            service.profile(userId)
        }
    @PutMapping("/")
    fun update(
        @RequestBody
        dto: UserUpdateDTO,
        request: HttpServletRequest
    ): ResponseEntity<UserDetailedVO?> =
        withUser(request) { userId ->
            service.update(userId, dto)
        }
    @DeleteMapping("/")
    fun delete(
        request: HttpServletRequest
    ): ResponseEntity<Unit?> =
        withUser(request) { userId ->
            service.delete(userId)
            request.session.removeAttribute("userId")
            request.session.removeAttribute("password")
        }
}