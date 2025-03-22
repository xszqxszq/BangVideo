package xyz.xszq.bang_video.common.dto

import java.io.Serializable

data class EncodingResult(
    val cid: Long,
    val resolutions: List<Int>
): Serializable