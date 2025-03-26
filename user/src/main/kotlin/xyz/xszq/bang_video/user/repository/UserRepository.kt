package xyz.xszq.bang_video.user.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import xyz.xszq.bang_video.user.entity.User

@Repository
interface UserRepository: MongoRepository<User, Long> {
    fun findByUsernameAndDeletedFalse(username: String): User?
    fun findByNicknameAndDeletedFalse(nickname: String): User?
    fun findByEmailAndDeletedFalse(email: String): User?
}