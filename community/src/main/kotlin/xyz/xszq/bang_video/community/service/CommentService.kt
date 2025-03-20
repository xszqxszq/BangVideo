package xyz.xszq.bang_video.community.service

import org.springframework.stereotype.Service
import xyz.xszq.bang_video.community.mapper.CommentMapper
import xyz.xszq.bang_video.community.repository.CommentRepository
import xyz.xszq.bang_video.community.vo.CommentVO

@Service
class CommentService(
    private val repository: CommentRepository,
    private val mapper: CommentMapper
) {
    fun list(videoId: Long): List<CommentVO> {
        return repository.findAllByVideoAndDeletedIsFalse(videoId).mapNotNull {
            mapper.toVO(it)
        }
    }
}