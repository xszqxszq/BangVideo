package xyz.xszq.bang_video.community.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.Named
import org.mapstruct.ReportingPolicy
import xyz.xszq.bang_video.common.formatTime
import xyz.xszq.bang_video.common.vo.UserVO
import xyz.xszq.bang_video.community.dto.CommentDTO
import xyz.xszq.bang_video.community.entity.Comment
import xyz.xszq.bang_video.community.vo.CommentInfoVO
import xyz.xszq.bang_video.community.vo.CommentVO
import java.time.Instant

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class CommentMapper {
    @Mappings(value = [
        Mapping(source = "time", target = "created"),
        Mapping(source = "time", target = "updated")
    ])
    abstract fun fromDTO(
        id: String,
        video: Long,
        user: Long,
        dto: CommentDTO,
        time: Instant
    ): Comment

    @Mappings(value = [
        Mapping(target = "created", qualifiedByName = ["formatTime"]),
        Mapping(target = "updated", qualifiedByName = ["formatTime"])
    ])
    abstract fun toInfoVO(comment: Comment?): CommentInfoVO?

    @Mappings(value = [
        Mapping(source = "comment.created", target = "created", qualifiedByName = ["formatTime"]),
        Mapping(source = "comment.created", target = "updated", qualifiedByName = ["formatTime"]),
        Mapping(source = "comment.id", target = "id"),
        Mapping(source = "user", target = "user")
    ])
    abstract fun toVO(comment: Comment, user: UserVO, replies: List<CommentVO> = listOf()): CommentVO

    @Named("formatTime")
    fun formatTime(time: Instant): String = time.formatTime()
}