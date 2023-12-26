package team.themoment.gsmNetworking.domain.user.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.user.domain.User
import team.themoment.gsmNetworking.domain.user.dto.UserRegistrationDto
import team.themoment.gsmNetworking.domain.user.repository.UserRepository

@Service
@Transactional(rollbackFor = [Exception::class])
class UserRegistrationService(
    private val userRepository: UserRepository,
    private val authenticatedUserManager: AuthenticatedUserManager,
) {

    /**
     * 유저 정보를 저장하는 메서드
     * 데이터의 중복을 막기 위해 식별자인 핸드폰으로 검증을 한다.
     *
     * @return 저장된 user 엔티티
     */
    fun execute(dto: UserRegistrationDto, authenticationId: Long): User {
        validateExistUserByPhoneNumber(dto.phoneNumber)
        validateExistUserByEmail(dto.email)
        if (userRepository.existsByAuthenticationId(authenticationId))
            throw ExpectedException("이미 등록되어있는 user입니다.", HttpStatus.CONFLICT)
        val user = User(
            authenticationId = authenticationId,
            name = dto.name,
            generation = dto.generation,
            email = dto.email,
            phoneNumber = dto.phoneNumber,
            snsUrl = dto.snsUrl,
            profileUrl = dto.profileUrl
        )
        return userRepository.save(user)
    }

    private fun validateExistUserByPhoneNumber(phoneNumber: String) {
        if (userRepository.existsByPhoneNumber(phoneNumber))
            throw ExpectedException("이미 존재하는 핸드폰 번호를 가진 유저 입니다.", HttpStatus.BAD_REQUEST)
    }

    private fun validateExistUserByEmail(email: String) {
        if (userRepository.existsByEmail(email))
            throw ExpectedException("이미 존재하는 email을 가진 유저 입니다.", HttpStatus.BAD_REQUEST)
    }

}
