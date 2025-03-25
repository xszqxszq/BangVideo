package xyz.xszq.bang_video.video.dto

data class AuditDTO(
    val cid: Long,
    val pass: Boolean,
    val frames: List<Int>
)
