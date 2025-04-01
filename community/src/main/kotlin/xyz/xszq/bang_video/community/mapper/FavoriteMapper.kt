package xyz.xszq.bang_video.community.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.Named
import org.mapstruct.ReportingPolicy
import xyz.xszq.bang_video.common.formatTime
import xyz.xszq.bang_video.common.vo.VideoVO
import xyz.xszq.bang_video.community.dto.FavoriteCreateDTO
import xyz.xszq.bang_video.community.entity.Favorite
import xyz.xszq.bang_video.community.vo.FavoriteVO
import java.time.Instant

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class FavoriteMapper {
    @Mappings(value = [
        Mapping(source = "videos", target = "videos"),
        Mapping(target = "created", qualifiedByName = ["formatTime"]),
        Mapping(target = "updated", qualifiedByName = ["formatTime"])
    ])
    abstract fun toVO(videos: List<VideoVO>, favorite: Favorite): FavoriteVO

    @Mappings(value = [
        Mapping(source = "time", target = "created", qualifiedByName = ["formatTime"]),
        Mapping(source = "time", target = "updated", qualifiedByName = ["formatTime"])
    ])
    abstract fun fromDTO(
        id: String,
        user: Long,
        dto: FavoriteCreateDTO,
        time: Instant,
        videos: List<Long> = emptyList()
    ): Favorite

    @Named("formatTime")
    fun formatTime(time: Instant): String = time.formatTime()
}