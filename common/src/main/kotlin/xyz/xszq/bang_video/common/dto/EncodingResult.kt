package xyz.xszq.bang_video.common.dto

data class EncodingResult(
    val cid: Long,
    val duration: Int,
    val fps: Double,
    val resolutions: List<Int>
)