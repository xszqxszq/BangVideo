package xyz.xszq.bang_video.user.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.user.dto.UserCreateDTO
import xyz.xszq.bang_video.user.dto.UserLoginDTO
import xyz.xszq.bang_video.user.service.UserService
import xyz.xszq.bang_video.user.vo.UserVO

@RestController
class AuthController(
    private val service: UserService
) {
    @PostMapping("/register")
    fun create(
        @RequestBody
        dto: UserCreateDTO
    ): UserVO? {
        return service.createUser(dto)
    }
    @PostMapping("/login")
    fun login(
        @RequestBody dto: UserLoginDTO,
        request: HttpServletRequest,
    ): ResponseEntity<Unit> {
        val loggedIn = request.session.run {
            service.validateSession(
                (getAttribute("userId") ?: return@run false) as Long,
                (getAttribute("password") ?: return@run false) as String
            )
        }
        if (loggedIn)
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build()
        return service.authenticate(dto) ?.let { user ->
            request.session.setAttribute("userId", user.id)
            request.session.setAttribute("password", user.password)
            ResponseEntity.ok().build()
        } ?: run {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }
    @PostMapping("/logout")
    fun logout(
        request: HttpServletRequest,
    ): ResponseEntity<Unit> {
        request.session.removeAttribute("userId")
        request.session.removeAttribute("password")
        return ResponseEntity.ok().build()
    }
}