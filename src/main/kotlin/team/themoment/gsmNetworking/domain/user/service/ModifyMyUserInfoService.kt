package team.themoment.gsmNetworking.domain.user.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.user.domain.User
import team.themoment.gsmNetworking.domain.user.dto.UserSaveInfoDto
import team.themoment.gsmNetworking.domain.user.repository.UserRepository

@Service
@Transactional(rollbackFor = [Exception::class])
class ModifyMyUserInfoService(
    private val userRepository: UserRepository,
) {

    fun execute(authenticationId: Long, userSaveInfoDto: UserSaveInfoDto) {
        val user = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("user를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        if (userSaveInfoDto.phoneNumber != user.phoneNumber)
            validateExistUserByPhoneNumber(userSaveInfoDto.phoneNumber)
        else if (userSaveInfoDto.email != user.email)
            validateExistUserByEmail(userSaveInfoDto.email)

        val updatedUser = User(
            id = user.id,
            authenticationId = authenticationId,
            name = userSaveInfoDto.name,
            generation = userSaveInfoDto.generation,
            email = userSaveInfoDto.email,
            phoneNumber = userSaveInfoDto.phoneNumber,
            snsUrl = userSaveInfoDto.snsUrl,
            profileUrl = userSaveInfoDto.profileUrl
        )

        userRepository.save(updatedUser)
    }

    private fun validateExistUserByPhoneNumber(phoneNumber: String) {
        if (userRepository.existsByPhoneNumber(phoneNumber))
            throw ExpectedException("이미 사용중인 핸드폰 번호입니다.", HttpStatus.BAD_REQUEST)
    }

    private fun validateExistUserByEmail(email: String) {
        if (userRepository.existsByEmail(email))
            throw ExpectedException("이미 사용중인 이메일입니다.", HttpStatus.BAD_REQUEST)
    }
}
