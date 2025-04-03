package xyz.xszq.bang_video.community.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import xyz.xszq.bang_video.community.entity.Notification

@Repository
interface NotificationRepository: MongoRepository<Notification, String> {
    fun findByUserOrderByTimeDesc(userId: Long): List<Notification>
    fun countByUserAndUnreadTrue(userId: Long): Int
}