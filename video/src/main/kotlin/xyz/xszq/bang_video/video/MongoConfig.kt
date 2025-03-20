package xyz.xszq.bang_video.video

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.index.Index
import xyz.xszq.bang_video.video.entity.Video

@Configuration
class MongoConfig {
    @Bean
    fun autoIndex(mongoTemplate: MongoTemplate) =
        mongoTemplate.indexOps(Video::class.java).apply {
            ensureIndex(Index().on("tags", Sort.Direction.ASC))
        }
}