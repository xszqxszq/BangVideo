package xyz.xszq.bang_video.community.service

import org.mapstruct.Context
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import xyz.xszq.bang_video.community.dto.CommentDTO
import xyz.xszq.bang_video.community.entity.Comment
import xyz.xszq.bang_video.community.mapper.CommentMapper
import xyz.xszq.bang_video.community.repository.CommentRepository
import xyz.xszq.bang_video.community.vo.CommentInfoVO
import xyz.xszq.bang_video.community.vo.CommentVO
import java.time.LocalDateTime
import java.util.*

@Service
class CommentService(
    private val repository: CommentRepository,
    private val mapper: CommentMapper
) {
    fun list(videoId: Long): List<CommentVO> {
        val comments = repository.findAllByVideoAndDeletedIsFalse(videoId)
        return toCommentVOList(comments)
    }
    fun create(videoId: Long, userId: Long, dto: CommentDTO): CommentInfoVO? {
        dto.parent ?.let { parentId ->
            val parent = repository.findByIdOrNull(parentId) ?: throw Exception("NotFound")
            if (parent.video != videoId)
                throw Exception("DifferentVideo")
        }
        val id = UUID.randomUUID().toString()
        val time = LocalDateTime.now()
        val comment = mapper.fromDTO(id, videoId, userId, dto, time)
        repository.save(comment)
        return mapper.toInfoVO(comment)
    }
    fun update(commentId: String, userId: Long, dto: CommentDTO): CommentInfoVO? {
        val comment = getComment(commentId, userId)
        comment.content = dto.content
        comment.updated = LocalDateTime.now()
        repository.save(comment)
        return mapper.toInfoVO(comment)
    }
    fun delete(commentId: String, userId: Long) {
        val comment = getComment(commentId, userId)
        comment.deleted = true
        comment.updated = LocalDateTime.now()
        repository.save(comment)
    }
    fun getComment(commentId: String, userId: Long? = null): Comment {
        val comment = repository.findByIdAndDeletedIsFalse(commentId)
            ?: throw Exception("NotFound")
        userId ?.let {
            if (comment.user != userId)
                throw Exception("NotOwner")
        }
        return comment
    }
    fun toCommentVOList(comments: List<Comment>): List<CommentVO> {
        val grouped = comments.groupBy { it.parent }
        return grouped[null]?.map { toNestedVO(it, grouped) } ?: emptyList()
    }

    fun toNestedVO(
        comment: Comment,
        @Context
        commentsByParent: Map<String?, List<Comment>>
    ): CommentVO {
        val vo = mapper.toVO(comment)
        vo.replies = resolveChildren(comment.id, commentsByParent)
        return vo
    }

    private fun resolveChildren(
        parentId: String?,
        commentsByParent: Map<String?, List<Comment>>
    ): List<CommentVO> {
        return commentsByParent[parentId]
            ?.map { toNestedVO(it, commentsByParent) }
            ?: emptyList()
    }
}