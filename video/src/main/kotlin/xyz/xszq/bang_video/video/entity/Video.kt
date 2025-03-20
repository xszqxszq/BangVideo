package xyz.xszq.bang_video.video.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("video")
@CompoundIndexes(value = [
    CompoundIndex(name = "tags_index", def = "{tags: 1}", background = true),
    CompoundIndex(name = "category_index", def = "{category: 1}", background = true)
])
data class Video(
    @Id
    val id: Long,
    var cid: Long,
    var title: String,
    var description: String,
    var cover: String,
    var owner: Long,
    var duration: Int,
    var category: Int,
    var tags: List<String> = emptyList(),
    var likes: Long = 0,
    var views: Long = 0,
    var favorites: Long = 0,
    var deleted: Boolean = false,
    var published: Boolean = false,
    var created: LocalDateTime = LocalDateTime.now(),
    var updated: LocalDateTime = LocalDateTime.now()
)
