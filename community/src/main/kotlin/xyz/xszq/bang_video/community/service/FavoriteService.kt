package xyz.xszq.bang_video.community.service

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import xyz.xszq.bang_video.common.vo.VideoVO
import xyz.xszq.bang_video.community.dto.FavoriteCreateDTO
import xyz.xszq.bang_video.community.entity.Favorite
import xyz.xszq.bang_video.community.mapper.FavoriteMapper
import xyz.xszq.bang_video.community.repository.FavoriteRepository
import xyz.xszq.bang_video.community.vo.FavoriteVO
import java.time.LocalDateTime
import java.util.*

@Service
class FavoriteService(
    private val repository: FavoriteRepository,
    private val mapper: FavoriteMapper,
    private val rabbitTemplate: RabbitTemplate
) {
    fun list(userId: Long): List<FavoriteVO> {
        return repository.findAllByUserAndDeletedFalse(userId).map { mapper.toVO(listOf(), it) }
    }
    fun create(dto: FavoriteCreateDTO, userId: Long): FavoriteVO? {
        val id = UUID.randomUUID().toString()
        val time =  LocalDateTime.now()
        val favorite = mapper.fromDTO(id, userId, dto, time)
        repository.save(favorite)
        return mapper.toVO(listOf(), favorite)
    }
    fun info(favoriteId: String): FavoriteVO? {
        val favorite = getFavorite(favoriteId)
        val videos = kotlin.runCatching {
            @Suppress("UNCHECKED_CAST")
            rabbitTemplate.convertSendAndReceive(
                "video.info_batch", favorite.videos
            ) as List<VideoVO>
        }.onFailure {
            it.printStackTrace()
        }.getOrNull() ?: return null
        return mapper.toVO(videos, favorite)
    }
    fun delete(favoriteId: String, userId: Long) {
        val favorite = getFavorite(favoriteId, userId)
        favorite.deleted = true
        favorite.updated = LocalDateTime.now()
        repository.save(favorite)
    }
    fun addVideo(favoriteId: String, videoId: Long, userId: Long) {
        val favorite = getFavorite(favoriteId, userId)
        if (!favorite.videos.contains(videoId))
            favorite.videos.add(videoId)
        favorite.updated = LocalDateTime.now()
        repository.save(favorite)
    }
    fun removeVideo(favoriteId: String, videoId: Long, userId: Long) {
        val favorite = getFavorite(favoriteId, userId)
        favorite.videos.remove(videoId)
        favorite.updated = LocalDateTime.now()
        repository.save(favorite)
    }
    fun getFavorite(favoriteId: String, userId: Long? = null): Favorite {
        val favorite = repository.findByIdOrNull(favoriteId) ?: throw Exception("NotFound")
        if (favorite.deleted)
            throw Exception("Deleted")
        userId ?.let {
            if (favorite.user != userId)
                throw Exception("NotOwner")
        }
        return favorite
    }
}