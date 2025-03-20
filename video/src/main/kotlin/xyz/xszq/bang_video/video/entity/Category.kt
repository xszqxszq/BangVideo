package xyz.xszq.bang_video.video.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("category")
data class Category(
    @Id
    val id: Int,
    val name: String,
    val description: String
)