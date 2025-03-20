package xyz.xszq.bang_video.video.service

import org.springframework.stereotype.Service
import xyz.xszq.bang_video.video.mapper.CategoryMapper
import xyz.xszq.bang_video.video.repository.CategoryRepository
import xyz.xszq.bang_video.video.vo.CategoryVO

@Service
class CategoryService(
    val categoryRepository: CategoryRepository,
    val mapper: CategoryMapper
) {
    fun list(): List<CategoryVO> {
        return categoryRepository.findAll().map { mapper.toVO(it) }
    }
}