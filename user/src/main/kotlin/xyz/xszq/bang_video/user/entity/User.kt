package xyz.xszq.bang_video.user.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("user")
data class User(
    @Id
    val id: Long,
    val username: String,
    var password: String,
    var email: String,
    var nickname: String,
    var avatar: String = "",
    var created: LocalDateTime,
    var updated: LocalDateTime,
    var deleted: Boolean = false
)