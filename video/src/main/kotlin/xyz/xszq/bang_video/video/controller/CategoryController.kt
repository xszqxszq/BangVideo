package xyz.xszq.bang_video.video.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.video.service.CategoryService
import xyz.xszq.bang_video.video.service.VideoService
import xyz.xszq.bang_video.video.vo.CategoryVO
import xyz.xszq.bang_video.video.vo.VideoVO

@RestController
@RequestMapping("/category")
class CategoryController(
    val service: CategoryService,
    val videoService: VideoService
) {
    @GetMapping("/")
    fun listCategories(): List<CategoryVO> {
        return service.list()
    }
    @GetMapping("/{id}")
    fun listVideos(
        @PathVariable
        id: Int
    ): ResponseEntity<List<VideoVO>?> {
        return videoService.findByCategory(id).let {
            ResponseEntity.ok(it)
        }
    }
}