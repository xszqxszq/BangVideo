package xyz.xszq.bang_video.community.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "favorite")
@CompoundIndexes(value = [
    CompoundIndex(name = "user_index",  def = "{user: 1}")
])
data class Favorite(
    @Id
    val id: String,
    val user: Long,
    val name: String,
    val videos: MutableList<Long> = mutableListOf(),
    val created: LocalDateTime = LocalDateTime.now(),
    var updated: LocalDateTime = LocalDateTime.now(),
    var deleted: Boolean = false
)
