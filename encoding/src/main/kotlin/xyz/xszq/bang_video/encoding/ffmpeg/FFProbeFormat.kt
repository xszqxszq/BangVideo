package xyz.xszq.bang_video.encoding.ffmpeg

import kotlinx.serialization.Serializable

@Serializable
data class FFProbeFormat(
    val filename: String,
    val duration: String? = null
)
