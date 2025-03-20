package xyz.xszq.bang_video.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository
import xyz.xszq.bang_video.user.entity.User

@Repository
@EnableJpaRepositories
interface UserRepository: JpaRepository<User, Long> {
    fun findByUsernameAndDeletedFalse(username: String): User?
    fun findByNicknameAndDeletedFalse(nickname: String): User?
    fun findByEmailAndDeletedFalse(email: String): User?
}