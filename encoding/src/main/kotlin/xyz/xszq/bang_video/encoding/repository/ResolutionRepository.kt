package xyz.xszq.bang_video.encoding.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import xyz.xszq.bang_video.encoding.entity.Resolution

@Repository
interface ResolutionRepository: MongoRepository<Resolution, Int> {
}