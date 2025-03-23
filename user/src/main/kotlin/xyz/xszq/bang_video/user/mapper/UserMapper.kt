package xyz.xszq.bang_video.user.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.Named
import org.mapstruct.ReportingPolicy
import xyz.xszq.bang_video.user.dto.UserCreateDTO
import xyz.xszq.bang_video.user.entity.User
import xyz.xszq.bang_video.user.vo.UserDetailedVO
import xyz.xszq.bang_video.user.vo.UserVO
import java.time.LocalDateTime

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class UserMapper {
    val static = System.getenv("STATIC_SERVER") ?: "http://localhost:7002"

    @Mappings(value = [
        Mapping(target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        Mapping(target = "avatar", qualifiedByName = ["avatarUrl"]),
    ])
    abstract fun toVO(user: User?): UserVO?

    @Mappings(value = [
        Mapping(target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        Mapping(target = "avatar", qualifiedByName = ["avatarUrl"]),
    ])
    abstract fun toDetailedVO(user: User?): UserDetailedVO?

    @Mappings(value = [
        Mapping(source = "time", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        Mapping(source = "time", target = "updated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ])
    abstract fun fromDTO(
        id: Long,
        password: String,
        time: LocalDateTime,
        dto: UserCreateDTO
    ): User

    @Named("avatarUrl")
    fun avatarUrl(uuid: String) = "$static/avatar/$uuid"
}