package xyz.xszq.bang_video.common.vo

data class VideoVO(
    val id: Long,
    val cid: Long,
    val title: String,
    val description: String,
    val cover: String,
    val owner: UserVO,
    val duration: Int,
    val category: Int,
    val tags: List<String>,
    val views: Long,
    val likes: Long,
    val favorites: Long,
    val created: String,
    val updated: String,
    val playlist: String,
    val auditPassed: Boolean? = null,
    val auditMessage: String? = null,
)
