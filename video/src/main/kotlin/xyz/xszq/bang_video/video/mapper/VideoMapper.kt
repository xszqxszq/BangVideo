package xyz.xszq.bang_video.video.mapper

import org.mapstruct.*
import xyz.xszq.bang_video.common.vo.UserVO
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
        Mapping(source = "video.created", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        Mapping(source = "video.updated", target = "updated", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        Mapping(source = "video.id", target = "id"),
        Mapping(source = "owner", target = "owner"),
        Mapping(source = "cover", target = "cover"),
    ])
    fun toVO(
        video: Video?,
        owner: UserVO,
        cover: String = run {
            val static = System.getenv("STATIC_SERVER") ?: ""
            "$static/cover/${video?.cover}"
        },
        playlist: String = run {
            val static = System.getenv("STATIC_SERVER") ?: ""
            "$static/video/${video?.cid}/playlist.m3u8"
        }
    ): VideoVO?

    fun update(dto: VideoDTO, @MappingTarget entity: Video)
}