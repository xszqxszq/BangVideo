package xyz.xszq.bang_video.video.repository

import org.springframework.data.mongodb.repository.MongoRepository
import xyz.xszq.bang_video.video.entity.Video

interface VideoRepository: MongoRepository<Video, Long> {
    fun findAllByIdIn(ids: List<Long>): List<Video>
}