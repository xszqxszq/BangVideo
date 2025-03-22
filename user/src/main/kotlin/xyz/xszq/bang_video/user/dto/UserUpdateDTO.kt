package xyz.xszq.bang_video.user.dto

data class UserUpdateDTO(
    val password: String ?= null,
    val email: String ?= null,
    val nickname: String ?= null,
    val avatar: String ?= null,
)
