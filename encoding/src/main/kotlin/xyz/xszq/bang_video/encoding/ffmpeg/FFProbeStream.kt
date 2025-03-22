package xyz.xszq.bang_video.encoding.ffmpeg

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FFProbeStream(
    val index: Int,
    @SerialName("codec_name")
    val codecName: String,
    @SerialName("codec_type")
    val codecType: String,
    val width: Int? = null,
    val height: Int? = null
)


