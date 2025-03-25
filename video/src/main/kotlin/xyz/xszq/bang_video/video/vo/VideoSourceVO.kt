package xyz.xszq.bang_video.video.vo

data class VideoSourceVO(
    val id: Long,
    val duration: Int,
    val fps: Double,
    val resolutions: List<ResolutionVO>,
    val auditMessage: String? = null,
)
