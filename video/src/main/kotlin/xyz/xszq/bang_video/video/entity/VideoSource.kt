package xyz.xszq.bang_video.video.entity

import org.springframework.data.mongodb.core.mapping.Document

@Document("video_source")
data class VideoSource(
    val id: Long,
    val succeeded: Boolean,
    val duration: Int,
    val fps: Double,
    val resolutions: List<Int>
)