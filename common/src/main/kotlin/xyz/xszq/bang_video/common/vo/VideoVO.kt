package xyz.xszq.bang_video.common.vo

import java.time.LocalDateTime

data class VideoVO(
    val id: Long,
    val cid: Long,
    val title: String,
    val description: String,
    val cover: String,
    val owner: Long,
    val duration: Int,
    val category: Int,
    val tags: List<String>,
    val views: Long,
    val likes: Long,
    val favorites: Long,
    val created: LocalDateTime,
    val updated: LocalDateTime
)
