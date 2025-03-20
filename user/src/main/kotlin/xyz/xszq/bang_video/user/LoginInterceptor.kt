package xyz.xszq.bang_video.user

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor
import xyz.xszq.bang_video.user.service.UserService

class LoginInterceptor(
    private val service: UserService,
): HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val userId = request.session.getAttribute("userId") as? Long
        val password = request.session.getAttribute("password") as? String

        if (userId == null || password == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "未登录")
            return false
        }
        return service.authenticateSession(userId, password) ?.let {
            true
        } ?: run {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "登录已失效")
            false
        }
    }
}