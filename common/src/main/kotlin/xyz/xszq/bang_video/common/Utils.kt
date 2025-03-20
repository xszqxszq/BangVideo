package xyz.xszq.bang_video.common

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import xyz.xszq.bang_video.common.feign.VideoFeignService

fun exceptionHandler(throwable: Throwable): HttpStatus =
    when (throwable.message) {
        "NotFound" -> HttpStatus.NOT_FOUND
        "NotOwner", "NotPublished" -> HttpStatus.FORBIDDEN
        "Deleted" -> HttpStatus.GONE
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }

inline fun <T> action(handler: () -> T?): ResponseEntity<T?> {
    val result = runCatching {
        handler()
    }.onFailure {
        return ResponseEntity.status(exceptionHandler(it))
            .body(null)
    }.getOrNull()
    return ResponseEntity.ok(result)
}
inline fun <T> withUser(
    request: HttpServletRequest,
    handler: (Long) -> T?
): ResponseEntity<T?> {
    val userId = request.session.getAttribute("userId") ?.let {
        it as Long
    } ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

    val result = runCatching {
        handler(userId)
    }.onFailure {
        it.printStackTrace()
        return ResponseEntity.status(exceptionHandler(it))
            .body(null)
    }.getOrNull()
    return ResponseEntity.ok(result)
}
inline fun <T> VideoFeignService.action(
    videoId: Long,
    handler: () -> T?
): ResponseEntity<T?> {
    kotlin.runCatching {
        getInfo(videoId)
    }.onFailure {
        return ResponseEntity.notFound().build()
    }
    return action(handler)
}
inline fun <T> VideoFeignService.withUser(
    videoId: Long,
    request: HttpServletRequest,
    handler: (Long) -> T?
): ResponseEntity<T?> {
    kotlin.runCatching {
        getInfo(videoId) ?: throw Exception("NotFound")
    }.onFailure {
        return ResponseEntity.notFound().build()
    }
    return withUser(request, handler)
}