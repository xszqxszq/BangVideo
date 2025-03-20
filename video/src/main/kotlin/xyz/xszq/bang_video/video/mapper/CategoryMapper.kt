package xyz.xszq.bang_video.video.mapper

import org.mapstruct.Mapper
import xyz.xszq.bang_video.video.entity.Category
import xyz.xszq.bang_video.video.vo.CategoryVO

@Mapper
interface CategoryMapper {
    fun toVO(category: Category): CategoryVO
}