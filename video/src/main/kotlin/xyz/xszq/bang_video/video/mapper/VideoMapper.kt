package xyz.xszq.bang_video.video.mapper

import org.mapstruct.*
import xyz.xszq.bang_video.common.vo.VideoVO
import xyz.xszq.bang_video.video.dto.VideoDTO
import xyz.xszq.bang_video.video.entity.Video
import java.time.LocalDateTime

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface VideoMapper {
    @Mappings(value = [
        Mapping(target = "id", source = "videoId"),
        Mapping(target = "owner", source = "userId"),
        Mapping(target = "created", source = "time"),
        Mapping(target = "updated", source = "time"),
    ])
    fun fromDTO(
        dto: VideoDTO,
        videoId: Long,
        userId: Long,
        duration: Int,
        time: LocalDateTime
    ): Video

    @Mappings(value = [
        Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        Mapping(source = "updated", target = "updated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ])
    fun toVO(video: Video?): VideoVO?

    fun update(dto: VideoDTO, @MappingTarget entity: Video)
}