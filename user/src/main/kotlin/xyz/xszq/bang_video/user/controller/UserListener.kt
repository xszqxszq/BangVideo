package xyz.xszq.bang_video.user.controller

import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import xyz.xszq.bang_video.common.vo.UserVO
import xyz.xszq.bang_video.user.service.UserService

@Component
class UserListener(
    private val service: UserService
) {
    @RabbitListener(queuesToDeclare = [Queue("user.info")])
    fun info(ids: List<Long>): List<UserVO> {
        return service.getUserByIdBatch(ids)
    }
}