package xyz.xszq.bang_video.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "user")
data class User(
    @Id
    val id: Long,
    val username: String,
    var password: String,
    var email: String,
    var nickname: String,
    @Column(
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    var created: LocalDateTime,
    @Column(
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
    )
    var updated: LocalDateTime,
    var deleted: Boolean
)