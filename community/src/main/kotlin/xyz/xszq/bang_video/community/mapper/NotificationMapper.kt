package xyz.xszq.bang_video.community.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.Named
import org.mapstruct.ReportingPolicy
import xyz.xszq.bang_video.common.formatTime
import xyz.xszq.bang_video.common.vo.UserVO
import xyz.xszq.bang_video.common.vo.VideoVO
import xyz.xszq.bang_video.community.entity.Notification
import xyz.xszq.bang_video.community.vo.NotificationVO
import java.time.Instant

@Mapper(unmappedSourcePolicy =  ReportingPolicy.IGNORE)
abstract class NotificationMapper {
    @Mappings(value = [
        Mapping(source = "notify.time", target = "time", qualifiedByName = ["formatTime"]),
        Mapping(source = "notify.id", target = "id"),
        Mapping(source = "sender", target = "sender"),
        Mapping(source = "video", target = "video"),
    ])
    abstract fun toVO(notify: Notification, sender: UserVO?, video: VideoVO): NotificationVO

    @Named("formatTime")
    fun formatTime(time: Instant): String = time.formatTime()
}