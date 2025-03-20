package xyz.xszq.bang_video.community.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import xyz.xszq.bang_video.community.entity.Comment

@Repository
interface CommentRepository: MongoRepository<Comment, String> {
    fun findAllByVideoAndDeletedIsFalse(video: Long): List<Comment>
}