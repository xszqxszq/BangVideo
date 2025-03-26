package xyz.xszq.bang_video.encoding.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("resolution")
data class Resolution(
    @Id
    val id: Int,
    val name: String,
    val width: Int,
    val height: Int
)
