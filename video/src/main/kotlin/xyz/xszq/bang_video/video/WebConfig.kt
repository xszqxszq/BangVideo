package xyz.xszq.bang_video.video

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import xyz.xszq.bang_video.common.InternalInterceptor
import xyz.xszq.bang_video.common.LoginInterceptor

@Configuration
class WebConfig : WebMvcConfigurer {
    @Lazy
    @Autowired
    private lateinit var userFeignService: UserFeignService
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LoginInterceptor(userFeignService))
            .addPathPatterns("/profile/**")
        registry.addInterceptor(InternalInterceptor())
            .addPathPatterns("/internal/**")
    }
}