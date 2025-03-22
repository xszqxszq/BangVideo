package xyz.xszq.bang_video.video.repository

import org.springframework.data.mongodb.repository.MongoRepository
import xyz.xszq.bang_video.video.entity.VideoSource

interface VideoSourceRepository: MongoRepository<VideoSource, Long> {
    fun findByIdAndSucceededTrue(id: Long): VideoSource?
}