package xyz.xszq.bang_video.community.service

import org.mapstruct.Context
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import xyz.xszq.bang_video.common.vo.UserVO
import xyz.xszq.bang_video.community.dto.CommentDTO
import xyz.xszq.bang_video.community.entity.Comment
import xyz.xszq.bang_video.community.mapper.CommentMapper
import xyz.xszq.bang_video.community.repository.CommentRepository
import xyz.xszq.bang_video.community.vo.CommentInfoVO
import xyz.xszq.bang_video.community.vo.CommentVO
import java.time.Instant
import java.util.*

@Service
class CommentService(
    private val repository: CommentRepository,
    private val mapper: CommentMapper,
    private val rabbit: RabbitTemplate
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
        val time = Instant.now()
        val comment = mapper.fromDTO(id, videoId, userId, dto, time)
        repository.save(comment)
        return mapper.toInfoVO(comment)
    }
    fun update(commentId: String, userId: Long, dto: CommentDTO): CommentInfoVO? {
        val comment = getComment(commentId, userId)
        comment.content = dto.content
        comment.updated = Instant.now()
        repository.save(comment)
        return mapper.toInfoVO(comment)
    }
    fun delete(commentId: String, userId: Long) {
        val comment = getComment(commentId, userId)
        comment.deleted = true
        comment.updated = Instant.now()
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
        val users = getUserVO(comments.map { it.user }.toSet().toList()).associateBy { it.id }
        return grouped[null]?.map { toNestedVO(it, grouped, users) } ?: emptyList()
    }

    fun toNestedVO(
        comment: Comment,
        @Context
        commentsByParent: Map<String?, List<Comment>>,
        users: Map<Long, UserVO>
    ): CommentVO {
        val vo = mapper.toVO(comment, users[comment.user]!!)
        vo.replies = resolveChildren(comment.id, commentsByParent, users)
        return vo
    }

    private fun resolveChildren(
        parentId: String?,
        commentsByParent: Map<String?, List<Comment>>,
        users: Map<Long, UserVO>
    ): List<CommentVO> {
        return commentsByParent[parentId]
            ?.map { toNestedVO(it, commentsByParent, users) }
            ?: emptyList()
    }

    fun getUserVO(ids: List<Long>): List<UserVO> {
        return kotlin.runCatching {
            @Suppress("UNCHECKED_CAST")
            rabbit.convertSendAndReceive(
                "user.info", ids
            ) as List<UserVO>
        }.getOrNull() ?: emptyList()
    }
}