package xyz.xszq.bang_video.user.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import xyz.xszq.bang_video.user.vo.UserVO
import xyz.xszq.bang_video.user.entity.User
import xyz.xszq.bang_video.user.vo.UserInternalVO

@Mapper
interface UserMapper {
    @Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss")
    fun toVO(user: User?): UserVO?
    @Mappings(value = [
        Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        Mapping(source = "updated", target = "updated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ])
    fun toInternalVO(user: User?): UserInternalVO?
}