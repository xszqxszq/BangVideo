package xyz.xszq.bang_video.common

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor

class InternalInterceptor: HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        request.getHeader("x-request-source") ?.let { source ->
            if (source == "external") {
                response.sendError(HttpStatus.FORBIDDEN.value())
                return false
            }
        }
        return true
    }
}