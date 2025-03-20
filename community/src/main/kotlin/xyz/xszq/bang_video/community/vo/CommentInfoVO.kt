package xyz.xszq.bang_video.community.vo

data class CommentInfoVO(
    val id: String,
    val video: Long,
    val user: Long,
    val content: String,
    val parent: String? = null,
    val created: String,
    val updated: String,
    val likes: Int = 0,
)
