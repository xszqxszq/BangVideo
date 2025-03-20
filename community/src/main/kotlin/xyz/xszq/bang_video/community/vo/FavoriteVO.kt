package xyz.xszq.bang_video.community.vo

import xyz.xszq.bang_video.common.vo.VideoVO

data class FavoriteVO(
    val id: String,
    val user: Long,
    val name: String,
    val videos: List<VideoVO>,
    val created: String,
    val updated: String
)
