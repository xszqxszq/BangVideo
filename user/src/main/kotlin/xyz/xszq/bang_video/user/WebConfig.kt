package xyz.xszq.bang_video.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import xyz.xszq.bang_video.common.InternalInterceptor
import xyz.xszq.bang_video.user.service.UserService

@Configuration
class WebConfig : WebMvcConfigurer {
    @Lazy
    @Autowired
    private lateinit var service: UserService
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LoginInterceptor(service))
            .addPathPatterns("/profile/**")
        registry.addInterceptor(InternalInterceptor())
            .addPathPatterns("/internal/**")
    }
}