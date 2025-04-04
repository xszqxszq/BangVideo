package xyz.xszq.bang_video.community.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import xyz.xszq.bang_video.community.entity.Favorite

@Repository
interface FavoriteRepository: MongoRepository<Favorite, String> {
    fun findAllByUserAndDeletedFalse(userId: Long): List<Favorite>
}