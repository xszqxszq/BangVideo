package xyz.xszq.bang_video.user.dto

data class UserCreateDTO(
    val username: String,
    val password: String,
    val email: String,
    val nickname: String,
)
