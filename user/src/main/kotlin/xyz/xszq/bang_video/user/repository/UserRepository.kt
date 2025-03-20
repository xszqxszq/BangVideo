package xyz.xszq.bang_video.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository
import xyz.xszq.bang_video.user.entity.User

@Repository
@EnableJpaRepositories
interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByNickname(nickname: String): User?
    fun findByEmail(email: String): User?
}