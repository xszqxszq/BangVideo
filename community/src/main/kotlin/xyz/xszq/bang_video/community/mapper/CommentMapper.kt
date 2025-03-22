package xyz.xszq.bang_video.community.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import xyz.xszq.bang_video.community.dto.CommentDTO
import xyz.xszq.bang_video.community.entity.Comment
import xyz.xszq.bang_video.community.vo.CommentInfoVO
import xyz.xszq.bang_video.community.vo.CommentVO
import java.time.LocalDateTime

@Mapper
interface CommentMapper {
    @Mappings(value = [
        Mapping(source = "time", target = "created"),
        Mapping(source = "time", target = "updated")
    ])
    fun fromDTO(
        id: String,
        video: Long,
        user: Long,
        dto: CommentDTO,
        time: LocalDateTime,
        likes: Int = 0,
        deleted: Boolean = false
    ): Comment

    @Mappings(value = [
        Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        Mapping(source = "updated", target = "updated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ])
    fun toInfoVO(comment: Comment?): CommentInfoVO?

    @Mappings(value = [
        Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        Mapping(source = "updated", target = "updated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ])
    fun toVO(comment: Comment): CommentVO
}