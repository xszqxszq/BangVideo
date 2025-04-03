package xyz.xszq.bang_video.community.service

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import xyz.xszq.bang_video.common.vo.UserVO
import xyz.xszq.bang_video.common.vo.VideoVO
import xyz.xszq.bang_video.community.entity.Comment
import xyz.xszq.bang_video.community.entity.Notification
import xyz.xszq.bang_video.community.mapper.NotificationMapper
import xyz.xszq.bang_video.community.repository.NotificationRepository
import xyz.xszq.bang_video.community.vo.CommentInfoVO
import xyz.xszq.bang_video.community.vo.NotificationVO
import java.time.Instant
import java.util.UUID

@Service
class NotificationService(
    private val repository: NotificationRepository,
    private val mapper: NotificationMapper,
    private val rabbit: RabbitTemplate
) {
    fun notifyComment(userId: Long, comment: CommentInfoVO) {
        repository.save(Notification(
            UUID.randomUUID().toString(),
            userId,
            comment.user,
            comment.content,
            comment.video,
            Instant.now(),
            true
        ))
    }
    fun notifyAudit(video: VideoVO) {
        val message = when (video.auditPassed) {
            true -> "您的视频《${video.title}》已通过审核。"
            false -> "您的视频《${video.title}》未通过审核：${video.auditMessage}"
            else -> throw UnknownError()
        }
        repository.save(Notification(
            UUID.randomUUID().toString(),
            video.owner.id,
            null,
            message,
            video.id,
            Instant.now(),
            true
        ))
    }
    fun unread(userId: Long): Int {
        return repository.countByUserAndUnreadTrue(userId)
    }
    fun list(userId: Long): List<NotificationVO> {
        val notifications = repository.findByUserOrderByTimeDesc(userId)
        val users = getUserVO(notifications.map { it.user }.toSet().toList()).associateBy { it.id }
        val videos = getVideoVO(notifications.map { it.video }.toSet().toList()).associateBy { it.id }
        val result = notifications.mapNotNull { n ->
            mapper.toVO(
                n,
                n.sender ?.let { users[n.sender] },
                videos[n.video] ?: return@mapNotNull null
            )
        }
        val unread = notifications.filter { it.unread }
        unread.forEach {
            it.unread = false
        }
        repository.saveAll(unread)
        return result
    }

    fun getUserVO(ids: List<Long>): List<UserVO> {
        return kotlin.runCatching {
            @Suppress("UNCHECKED_CAST")
            rabbit.convertSendAndReceive(
                "user.info", ids
            ) as List<UserVO>
        }.getOrNull() ?: emptyList()
    }
    fun getVideoVO(ids: List<Long>): List<VideoVO> {
        return kotlin.runCatching {
            @Suppress("UNCHECKED_CAST")
            rabbit.convertSendAndReceive(
                "video.info", ids
            ) as List<VideoVO>
        }.onFailure {
            it.printStackTrace()
        }.getOrNull() ?: emptyList()
    }
}