package xyz.xszq.bang_video.community.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.Named
import org.springframework.beans.factory.annotation.Autowired
import xyz.xszq.bang_video.common.vo.VideoVO
import xyz.xszq.bang_video.community.VideoFeignService
import xyz.xszq.bang_video.community.dto.FavoriteCreateDTO
import xyz.xszq.bang_video.community.entity.Favorite
import xyz.xszq.bang_video.community.vo.FavoriteVO
import java.time.LocalDateTime

@Mapper(componentModel = "spring")
abstract class FavoriteMapper {
    @Autowired
    protected lateinit var videoFeignService: VideoFeignService

    @Mappings(value = [
        Mapping(source = "videos", target = "videos", qualifiedByName = ["mapVideos"]),
        Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        Mapping(source = "updated", target = "updated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ])
    abstract fun toVO(favorite: Favorite): FavoriteVO

    @Mappings(value = [
        Mapping(source = "time", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        Mapping(source = "time", target = "updated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ])
    abstract fun fromDTO(
        id: String,
        user: Long,
        dto: FavoriteCreateDTO,
        time: LocalDateTime,
        videos: List<Long> = emptyList(),
        deleted: Boolean = false
    ): Favorite

    @Named("mapVideos")
    fun mapVideos(videos: List<Long>): List<VideoVO> {
        if (videos.isEmpty()) return mutableListOf()
        return videoFeignService.batch(videos).toMutableList()
    }
}