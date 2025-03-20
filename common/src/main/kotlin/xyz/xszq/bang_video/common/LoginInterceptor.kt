package xyz.xszq.bang_video.common

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor
import xyz.xszq.bang_video.common.feign.UserFeignService

class LoginInterceptor(
    private val userFeignService: UserFeignService
): HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any): Boolean {
        val session = request.session
        val userId = session.getAttribute("userId") as? Long
        val password = session.getAttribute("password") as? String

        if (userId == null || password == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "未登录")
            return false
        }

        val user = try {
            userFeignService.get(userId, password)
        } catch (e: Exception) {
            response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), "用户服务不可用")
            return false
        }

        return if (user != null) {
            true
        } else {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "登录已失效")
            false
        }
    }
}