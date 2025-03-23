package xyz.xszq.bang_video.community.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy
import xyz.xszq.bang_video.common.vo.VideoVO
import xyz.xszq.bang_video.community.dto.FavoriteCreateDTO
import xyz.xszq.bang_video.community.entity.Favorite
import xyz.xszq.bang_video.community.vo.FavoriteVO
import java.time.LocalDateTime

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class FavoriteMapper {
    @Mappings(value = [
        Mapping(source = "videos", target = "videos"),
        Mapping(target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        Mapping(target = "updated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ])
    abstract fun toVO(videos: List<VideoVO>, favorite: Favorite): FavoriteVO

    @Mappings(value = [
        Mapping(source = "time", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        Mapping(source = "time", target = "updated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ])
    abstract fun fromDTO(
        id: String,
        user: Long,
        dto: FavoriteCreateDTO,
        time: LocalDateTime,
        videos: List<Long> = emptyList()
    ): Favorite
}