package xyz.xszq.bang_video.video.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import xyz.xszq.bang_video.video.entity.Category

@Repository
interface CategoryRepository: MongoRepository<Category, Int> {
}