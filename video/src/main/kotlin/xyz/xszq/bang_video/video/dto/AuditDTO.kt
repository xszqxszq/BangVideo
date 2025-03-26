package xyz.xszq.bang_video.video.dto

data class AuditDTO(
    val id: Long,
    val cid: Long,
    val pass: Boolean,
    val frames: List<Int>
)
