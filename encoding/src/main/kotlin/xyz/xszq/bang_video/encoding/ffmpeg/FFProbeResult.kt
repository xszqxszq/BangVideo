package xyz.xszq.bang_video.encoding.ffmpeg

import kotlinx.serialization.Serializable

@Serializable
data class FFProbeResult(
    val streams: List<FFProbeStream>? = null,
    val format: FFProbeFormat? = null
)
