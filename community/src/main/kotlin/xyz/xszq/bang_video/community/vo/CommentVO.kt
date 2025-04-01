package xyz.xszq.bang_video.community.vo

import xyz.xszq.bang_video.common.vo.UserVO

data class CommentVO(
    val id: String,
    val video: Long,
    val user: UserVO,
    val content: String,
    val parent: String? = null,
    val created: String,
    val updated: String,
    val likes: Int = 0,
    var replies: List<CommentVO>? = null
)
