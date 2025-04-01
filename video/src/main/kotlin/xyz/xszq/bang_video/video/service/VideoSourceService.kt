package xyz.xszq.bang_video.video.service

import org.springframework.stereotype.Service
import xyz.xszq.bang_video.common.dto.EncodingResult
import xyz.xszq.bang_video.video.entity.VideoSource
import xyz.xszq.bang_video.video.repository.VideoSourceRepository

@Service
class VideoSourceService(
    private val repository: VideoSourceRepository,
) {
    val static = System.getenv("STATIC_SERVER") ?: "http://localhost:7002"
    fun add(cid: Long, result: EncodingResult?) {
        val source = result ?.let {
            VideoSource(
                id = result.cid,
                succeeded = true,
                duration = result.duration,
                fps = result.fps,
                resolutions = result.resolutions
            )
        } ?: VideoSource(
            id = cid,
            succeeded = false,
            duration = 0,
            fps = 0.0,
            resolutions = emptyList()
        )
        repository.save(source)
    }
}