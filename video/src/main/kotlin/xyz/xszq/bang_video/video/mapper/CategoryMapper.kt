package xyz.xszq.bang_video.video.mapper

import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import xyz.xszq.bang_video.video.entity.Category
import xyz.xszq.bang_video.video.vo.CategoryVO

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CategoryMapper {
    fun toVO(category: Category): CategoryVO
}