package xyz.xszq.bang_video.user.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import xyz.xszq.bang_video.common.vo.UserInternalVO
import xyz.xszq.bang_video.user.vo.UserVO
import xyz.xszq.bang_video.user.entity.User
import xyz.xszq.bang_video.user.mapper.UserMapper
import xyz.xszq.bang_video.user.repository.UserRepository
import xyz.xszq.bang_video.user.dto.UserCreateDTO
import xyz.xszq.bang_video.user.dto.UserLoginDTO
import xyz.xszq.bang_video.user.dto.UserUpdateDTO
import xyz.xszq.bang_video.user.vo.UserDetailedVO
import java.time.LocalDateTime

@Service
class UserService(
    private val repository: UserRepository,
    private val userIdGenerator: UserIdGeneratorService,
    private val mapper: UserMapper
) {
    private val encoder = BCryptPasswordEncoder()
    @OptIn(ExperimentalStdlibApi::class)
    @Transactional
    fun createUser(
        dto: UserCreateDTO,
    ): UserVO? {
        // TODO: Username/Nickname/Email Duplication check
        val user = mapper.fromDTO(
            id = userIdGenerator.generateUserId(),
            password = encoder.encode(dto.password),
            time = LocalDateTime.now(),
            dto = dto
        )
        repository.save(user)
        return mapper.toVO(user)
    }
    @Transactional
    fun getUserById(
        id: Long,
    ): UserVO? = mapper.toVO(repository.findByIdOrNull(id))
    @Transactional
    fun getUserByNickname(
        nickname: String,
    ): UserVO? = mapper.toVO(repository.findByNicknameAndDeletedFalse(nickname))
    fun validateSession(userId: Long, password: String): Boolean {
        val user = repository.findByIdOrNull(userId)?: return false
        return user.password == password
    }
    @Transactional
    fun authenticate(
        request: UserLoginDTO
    ): User? {
        val user = repository.findByUsernameAndDeletedFalse(request.username)
            ?: repository.findByEmailAndDeletedFalse(request.username)
            ?: return null
        return if (encoder.matches(request.password, user.password))
            user
        else
            null
    }
    @Transactional
    fun authenticateSession(userId: Long, password: String): UserInternalVO? {
        val user = repository.findByIdOrNull(userId)
            ?: return null
        return if (user.password == password)
            mapper.toInternalVO(user)
        else
            null
    }

    @Transactional
    fun profile(
        id: Long,
    ): UserDetailedVO? = mapper.toDetailedVO(repository.findByIdOrNull(id))
    @Transactional
    fun update(userId: Long, dto: UserUpdateDTO): UserDetailedVO? {
        val user = repository.findByIdOrNull(userId)
            ?: throw Exception("NotFound")
        dto.password ?.let {
            user.password = encoder.encode(dto.password)
        }
        // TODO: Not Atomic
        dto.email ?.let {
            repository.findByEmailAndDeletedFalse(dto.email) ?.let {
                throw Exception("Duplicate")
            }
            user.email = dto.email
        }
        // TODO: Not Atomic
        dto.nickname ?.let {
            repository.findByNicknameAndDeletedFalse(dto.nickname) ?.let {
                throw Exception("Duplicate")
            }
            user.nickname = dto.nickname
        }
        // TODO: Not Atomic
        dto.avatar ?.let {
            user.avatar = dto.avatar
        }
        user.updated = LocalDateTime.now()
        repository.save(user)
        return mapper.toDetailedVO(user)
    }
    @Transactional
    fun delete(userId: Long) {
        val user = repository.findByIdOrNull(userId)
            ?: throw Exception("NotFound")
        user.deleted = true
        user.updated = LocalDateTime.now()
        repository.save(user)
    }
}