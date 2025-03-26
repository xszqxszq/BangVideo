package xyz.xszq.bang_video.encoding

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import xyz.xszq.bang_video.encoding.entity.Resolution
import xyz.xszq.bang_video.encoding.repository.ResolutionRepository

@Component
class Initializer(
    private val repository: ResolutionRepository
): ApplicationRunner {
    fun exists() = repository.findByIdOrNull(480) != null

    override fun run(args: ApplicationArguments?) {
        if (!exists()) {
            repository.saveAll(buildList {
                add(Resolution(480, "清晰", 640, 480))
                add(Resolution(720, "标清", 1280, 720))
                add(Resolution(1080, "超清", 1920, 1080))
                add(Resolution(2160, "4K", 3840, 2160))
            })
        }
    }

}