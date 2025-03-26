package xyz.xszq.bang_video.user.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import xyz.xszq.bang_video.user.dto.UserCreateDTO
import xyz.xszq.bang_video.user.dto.UserLoginDTO
import xyz.xszq.bang_video.user.dto.UserUpdateDTO
import xyz.xszq.bang_video.user.entity.User
import xyz.xszq.bang_video.user.mapper.UserMapper
import xyz.xszq.bang_video.user.repository.UserRepository
import xyz.xszq.bang_video.user.vo.UserDetailedVO
import xyz.xszq.bang_video.user.vo.UserVO
import java.time.LocalDateTime

@Service
class UserService(
    private val repository: UserRepository,
    private val userIdGenerator: UserIdGeneratorService,
    private val mapper: UserMapper
) {
    private val encoder = BCryptPasswordEncoder()
    @OptIn(ExperimentalStdlibApi::class)
    fun createUser(
        dto: UserCreateDTO,
    ): UserVO? {
        // TODO: Username/Nickname/Email Duplication check
        dto.password = encoder.encode(dto.password)
        val user = mapper.fromDTO(
            id = userIdGenerator.generateUserId(),
            time = LocalDateTime.now(),
            dto = dto
        )
        repository.save(user)
        return mapper.toVO(user)
    }
    fun getUserById(
        id: Long,
    ): UserVO? = mapper.toVO(repository.findByIdOrNull(id))
    fun getUserByNickname(
        nickname: String,
    ): UserVO? = mapper.toVO(repository.findByNicknameAndDeletedFalse(nickname))
    fun validateSession(userId: Long, password: String): Boolean {
        val user = repository.findByIdOrNull(userId)?: return false
        return user.password == password
    }
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

    fun profile(
        id: Long,
    ): UserDetailedVO? = mapper.toDetailedVO(repository.findByIdOrNull(id))
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
    fun delete(userId: Long) {
        val user = repository.findByIdOrNull(userId)
            ?: throw Exception("NotFound")
        user.deleted = true
        user.updated = LocalDateTime.now()
        repository.save(user)
    }
}