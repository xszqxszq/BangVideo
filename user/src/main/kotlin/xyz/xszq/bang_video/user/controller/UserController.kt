package xyz.xszq.bang_video.user.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.user.dto.UserCreateDTO
import xyz.xszq.bang_video.user.dto.UserLoginDTO
import xyz.xszq.bang_video.user.service.UserService
import xyz.xszq.bang_video.user.vo.UserVO

@RestController
class UserController(
    private val userService: UserService
) {
    @PostMapping("/")
    fun create(
        @RequestBody request: UserCreateDTO
    ): UserVO? {
        return userService.createUser(request)
    }
    @GetMapping("/{userId}")
    fun infoById(
        @PathVariable
        userId: Long
    ): UserVO? {
        return userService.getUserById(userId)
    }
    @GetMapping("/name/{name}")
    fun infoByName(
        @PathVariable
        name: String
    ): UserVO? {
        return userService.getUserByNickname(name)
    }
    @PostMapping("/login")
    fun login(
        @RequestBody dto: UserLoginDTO,
        request: HttpServletRequest,
    ): ResponseEntity<Unit> {
        val loggedIn = request.session.run {
            userService.validateSession(
                (getAttribute("userId") ?: return@run false) as Long,
                (getAttribute("password") ?: return@run false) as String
            )
        }
        if (loggedIn)
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build()
        return userService.authenticate(dto) ?.let { user ->
            request.session.setAttribute("userId", user.id)
            request.session.setAttribute("password", user.password)
            ResponseEntity.ok().build()
        } ?: run {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }
    @PostMapping("/logout")
    fun login(
        request: HttpServletRequest,
    ): ResponseEntity<Unit> {
        request.session.removeAttribute("userId")
        request.session.removeAttribute("password")
        return ResponseEntity.ok().build()
    }
}