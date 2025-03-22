package xyz.xszq.bang_video.encoding.service

import org.springframework.stereotype.Service
import xyz.xszq.bang_video.encoding.entity.Resolution
import xyz.xszq.bang_video.encoding.repository.ResolutionRepository

@Service
class ResolutionService(
    private val repository: ResolutionRepository
) {
    fun list(): List<Resolution> = repository.findAll().sortedBy { it.id }
}