package xyz.xszq.bang_video.video.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import xyz.xszq.bang_video.common.vo.VideoVO
import xyz.xszq.bang_video.video.service.CategoryService
import xyz.xszq.bang_video.video.service.VideoService
import xyz.xszq.bang_video.video.vo.CategoryVO

@RestController
class SearchController(
    private val service: VideoService,
    private val category: CategoryService,
) {
    @GetMapping("/category/")
    fun category(): List<CategoryVO> {
        return category.list()
    }
    @GetMapping("/category/{id}")
    fun listVideos(
        @PathVariable
        id: Int
    ): ResponseEntity<List<VideoVO>?> {
        return service.findByCategory(id).let {
            ResponseEntity.ok(it)
        }
    }
    @GetMapping("/user/{userId}")
    fun user(
        @PathVariable userId: Long
    ): List<VideoVO> {
        return service.findByUser(userId)
    }
    @GetMapping("/tag/{tag}")
    fun list(
        @PathVariable tag: String
    ): List<VideoVO> {
        return service.findByTag(tag)
    }
    @GetMapping("/search/{keyword}")
    fun search(
        @PathVariable keyword: String,
    ): List<VideoVO> {
        return service.search(keyword)
    }
}