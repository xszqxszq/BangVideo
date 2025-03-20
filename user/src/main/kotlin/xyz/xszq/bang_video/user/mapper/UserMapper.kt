package xyz.xszq.bang_video.user.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import xyz.xszq.bang_video.common.vo.UserInternalVO
import xyz.xszq.bang_video.user.dto.UserCreateDTO
import xyz.xszq.bang_video.user.vo.UserVO
import xyz.xszq.bang_video.user.entity.User
import xyz.xszq.bang_video.user.vo.UserDetailedVO
import java.time.LocalDateTime

@Mapper
interface UserMapper {
    @Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss")
    fun toVO(user: User?): UserVO?
    @Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss")
    fun toDetailedVO(user: User?): UserDetailedVO?
    @Mappings(value = [
        Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        Mapping(source = "updated", target = "updated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ])
    fun toInternalVO(user: User?): UserInternalVO?
    @Mappings(value = [
        Mapping(source = "password", target = "password"),
        Mapping(source = "time", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        Mapping(source = "time", target = "updated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ])
    fun fromDTO(
        id: Long,
        password: String,
        time: LocalDateTime,
        dto: UserCreateDTO,
        deleted: Boolean = false,
    ): User
}