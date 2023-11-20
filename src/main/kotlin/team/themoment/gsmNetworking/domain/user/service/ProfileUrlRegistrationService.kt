package team.themoment.gsmNetworking.domain.user.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.user.domain.User
import team.themoment.gsmNetworking.domain.user.repository.UserRepository

@Service
@Transactional(rollbackFor = [Exception::class])
class ProfileUrlRegistrationService(
    private val userRepository: UserRepository
) {

    fun execute(authenticationId: Long, profileUrl: String) {
        val user = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("user를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        profileUrlRegistration(user, profileUrl)
    }

    fun profileUrlRegistration(user: User, profileUrl: String) {
        val addProfileUrlToUser = User(
            user.userId,
            user.authenticationId,
            user.name,
            user.generation,
            user.email,
            user.phoneNumber,
            user.snsUrl,
            profileUrl
        )

        userRepository.save(addProfileUrlToUser)
    }
}
