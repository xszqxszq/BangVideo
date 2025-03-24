package xyz.xszq.bang_video.video.entity

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "video")
data class VideoSearch(
    @Id
    val id: Long,
    val title: String,
    val description: String,
    val tags: List<String>,
)
