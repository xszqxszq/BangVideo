package xyz.xszq.bang_video.user.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import xyz.xszq.bang_video.user.vo.UserVO
import xyz.xszq.bang_video.user.entity.User
import xyz.xszq.bang_video.user.mapper.UserMapper
import xyz.xszq.bang_video.user.repository.UserRepository
import xyz.xszq.bang_video.user.dto.UserCreateDTO
import xyz.xszq.bang_video.user.dto.UserLoginDTO
import xyz.xszq.bang_video.user.vo.UserInternalVO
import java.time.LocalDateTime

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userIdGenerator: UserIdGeneratorService,
    private val userMapper: UserMapper
) {
    private val encoder = BCryptPasswordEncoder()
    @OptIn(ExperimentalStdlibApi::class)
    @Transactional
    fun createUser(
        request: UserCreateDTO,
    ): UserVO? {
        val userId = userIdGenerator.generateUserId()
        val hashedPassword = encoder.encode(request.password)

        val user = userRepository.save(User(
            id = userId,
            username = request.username,
            password = hashedPassword,
            email = request.email,
            nickname = request.nickname,
            created = LocalDateTime.now(),
            updated = LocalDateTime.now()
        ))
        return userMapper.toVO(user)
    }
    @Transactional
    fun getUserById(
        id: Long,
    ): UserVO? = userMapper.toVO(userRepository.findByIdOrNull(id))
    @Transactional
    fun getUserByNickname(
        nickname: String,
    ): UserVO? = userMapper.toVO(userRepository.findByNickname(nickname))
    fun validateSession(userId: Long, password: String): Boolean {
        val user = userRepository.findByIdOrNull(userId)?: return false
        return user.password == password
    }
    @Transactional
    fun authenticate(
        request: UserLoginDTO
    ): User? {
        val user = userRepository.findByUsername(request.username)
            ?: userRepository.findByEmail(request.username)
            ?: return null
        return if (encoder.matches(request.password, user.password))
            user
        else
            null
    }
    @Transactional
    fun authenticateSession(userId: Long, password: String): UserInternalVO? {
        val user = userRepository.findByIdOrNull(userId)
            ?: return null
        return if (user.password == password)
            userMapper.toInternalVO(user)
        else
            null
    }
}