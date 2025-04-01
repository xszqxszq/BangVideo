package xyz.xszq.bang_video.community.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("comment")
@CompoundIndexes(value = [
    CompoundIndex(name = "video_index",  def = "{video: 1}"),
    CompoundIndex(name = "parent_index",  def = "{parent: 1}"),
    CompoundIndex(name = "created_index",  def = "{created: 1}")
])
data class Comment(
    @Id
    val id: String,
    val video: Long,
    val user: Long,
    var content: String,
    val parent: String? = null,
    val created: Instant = Instant.now(),
    var updated: Instant = Instant.now(),
    val likes: Int = 0,
    var deleted: Boolean = false
)
