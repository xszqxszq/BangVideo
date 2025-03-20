package xyz.xszq.bang_video.community.vo

import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class CommentVO(
    @Id
    val id: String,
    val video: Long,
    val user: Long,
    val content: String,
    val parent: String? = null,
    val created: LocalDateTime = LocalDateTime.now(),
    val updated: LocalDateTime = LocalDateTime.now(),
    val likes: Int = 0,
)
