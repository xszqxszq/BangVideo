package xyz.xszq.bang_video.community.entity

import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("notification")
data class Notification(
    val id: String,
    val user: Long,
    val sender: Long? = null,
    val message: String,
    val video: Long,
    val time: Instant,
    var unread: Boolean
)
