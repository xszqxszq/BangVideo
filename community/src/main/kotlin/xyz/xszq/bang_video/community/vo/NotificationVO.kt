package xyz.xszq.bang_video.community.vo

import xyz.xszq.bang_video.common.vo.UserVO
import xyz.xszq.bang_video.common.vo.VideoVO

data class NotificationVO(
    val id: String,
    val sender: UserVO? = null,
    val message: String,
    val video: VideoVO,
    val time: String,
    val unread: Boolean
)
