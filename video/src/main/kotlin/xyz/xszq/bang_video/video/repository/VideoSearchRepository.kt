package xyz.xszq.bang_video.video.repository

import org.springframework.data.elasticsearch.annotations.Query
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import xyz.xszq.bang_video.video.entity.VideoSearch

interface VideoSearchRepository: ElasticsearchRepository<VideoSearch, Long> {
    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"title\", \"description\", \"tags\"]}}")
    fun search(keyword: String): List<VideoSearch>
}