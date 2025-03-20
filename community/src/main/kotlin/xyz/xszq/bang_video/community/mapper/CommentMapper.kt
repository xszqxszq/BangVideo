package xyz.xszq.bang_video.community.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import xyz.xszq.bang_video.community.entity.Comment
import xyz.xszq.bang_video.community.vo.CommentVO

@Mapper
interface CommentMapper {
    @Mappings(value = [
        Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        Mapping(source = "updated", target = "updated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ])
    fun toVO(comment: Comment?): CommentVO?
}