package xyz.xszq.bang_video.community.dto

data class CommentDTO(
    val video: Long,
    val content: String,
    val parent: String? = null
)
