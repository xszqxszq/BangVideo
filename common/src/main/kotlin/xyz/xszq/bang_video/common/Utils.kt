package xyz.xszq.bang_video.common

import jakarta.servlet.http.HttpServletRequest
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import xyz.xszq.bang_video.common.vo.VideoVO

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
fun RabbitTemplate.getVideoInfo(videoId: Long): VideoVO? {
    val result = convertSendAndReceive(
        "video.info", videoId
    )
    return when (result) {
        is VideoVO -> result
        else -> null
    }
}
inline fun <T> RabbitTemplate.withVideo(
    videoId: Long,
    handler: () -> T?
): ResponseEntity<T?> {
    kotlin.runCatching {
        getVideoInfo(videoId)
    }.getOrNull() ?: return ResponseEntity.notFound().build()
    return action(handler)
}
inline fun <T> RabbitTemplate.withUser(
    videoId: Long,
    request: HttpServletRequest,
    handler: (Long) -> T?
): ResponseEntity<T?> {
    kotlin.runCatching {
        getVideoInfo(videoId)
    }.getOrNull() ?: return ResponseEntity.notFound().build()
    return withUser(request, handler)
}
fun getIP(request: HttpServletRequest): String {
    return listOf(
        "CLIENT-IP",
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_CLIENT_IP",
        "HTTP_X_FORWARDED_FOR",
        "X-Real-IP"
    ).firstNotNullOfOrNull { header ->
        request.getHeader(header) ?.let { value ->
            value.split(",").firstOrNull() ?.trim()
        }
    } ?: request.remoteAddr
}
fun Int.toTime(): String {
    var now = this
    val hours = now / 3600
    now = now % 3600
    val minutes = now / 60
    val seconds = now % 60

    return if (hours != 0)
        "%d:%02d:%02d".format(hours, minutes, seconds)
    else
        "%02d:%02d".format(minutes, seconds)
}