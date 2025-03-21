package xyz.xszq.bang_video.video.service

import org.springframework.stereotype.Service
import xyz.xszq.bang_video.common.dto.EncodingResult
import xyz.xszq.bang_video.video.entity.VideoSource
import xyz.xszq.bang_video.video.repository.VideoSourceRepository
import xyz.xszq.bang_video.video.vo.ResolutionVO
import xyz.xszq.bang_video.video.vo.VideoSourceVO

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
                resolutions = result.resolutions
            )
        } ?: VideoSource(
            id = cid,
            succeeded = false,
            resolutions = emptyList()
        )
        repository.save(source)
    }
    fun get(cid: Long): VideoSourceVO? {
        return repository.findByIdAndSucceededTrue(cid) ?.let { source ->
            VideoSourceVO(cid, source.resolutions.map { resolution ->
                ResolutionVO(
                    id = resolution,
                    url = "$static/video/$cid/$resolution.mp4"
                )
            })
        } ?: throw Exception("NotFound")
    }
}