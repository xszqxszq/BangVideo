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
    val password: String,
    val email: String,
    val nickname: String,
    @Column(
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    val created: LocalDateTime,
    @Column(
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
    )
    val updated: LocalDateTime,
)