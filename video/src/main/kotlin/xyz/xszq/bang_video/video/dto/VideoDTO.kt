package xyz.xszq.bang_video.video.dto

data class VideoDTO(
    val cid: Long,
    val title: String,
    val description: String,
    val cover: String,
    val category: Int,
    val tags: List<String>,
)
