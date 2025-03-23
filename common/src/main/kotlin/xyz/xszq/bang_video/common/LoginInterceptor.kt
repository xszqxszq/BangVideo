package xyz.xszq.bang_video.common

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor

class LoginInterceptor: HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        request.session.getAttribute("userId") as? Long ?: run {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "未登录")
            return false
        }
        return true
    }
}