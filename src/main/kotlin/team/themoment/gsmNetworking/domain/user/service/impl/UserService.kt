package team.themoment.gsmNetworking.domain.user.service.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.mentee.service.DeleteMyMenteeInfoUseCase
import team.themoment.gsmNetworking.domain.user.domain.User
import team.themoment.gsmNetworking.domain.user.dto.ProfileUrlRegistrationDto
import team.themoment.gsmNetworking.domain.user.dto.UserInfoDto
import team.themoment.gsmNetworking.domain.user.dto.UserSaveInfoDto
import team.themoment.gsmNetworking.domain.user.repository.UserRepository
import team.themoment.gsmNetworking.domain.user.service.*

@Service
class UserService(
    private val userRepository: UserRepository,
) : GenerateUserUseCase,
    ModifyMyUserInfoUseCase,
    GenerateProfileUrlUseCase,
    DeleteMyUserInfoUseCase,
    QueryMyUserInfoUseCase {

    @Transactional(rollbackFor = [Exception::class])
    override fun generateUser(dto: UserSaveInfoDto, authenticationId: Long): User {
        validateExistUserByPhoneNumber(dto.phoneNumber)
        validateExistUserByEmail(dto.email)
        if (userRepository.existsByAuthenticationId(authenticationId))
            throw ExpectedException("이미 등록되어있는 user입니다.", HttpStatus.BAD_REQUEST)
        val user = User(
            authenticationId = authenticationId,
            name = dto.name,
            generation = dto.generation,
            email = dto.email,
            phoneNumber = dto.phoneNumber,
            snsUrl = dto.snsUrl,
            profileUrl = null
        )
        return userRepository.save(user)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun modifyMyUserInfo(authenticationId: Long, userSaveInfoDto: UserSaveInfoDto) {
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
            profileUrl = user.profileUrl
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

    @Transactional(rollbackFor = [Exception::class])
    override fun generateProfileUrl(authenticationId: Long, dto: ProfileUrlRegistrationDto) {
        val user = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("user를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        profileUrlRegistered(user, dto.profileUrl)
    }

    fun profileUrlRegistered(user: User, profileUrl: String?) {
        val userUpdatedProfileUrl = User(
            id = user.id,
            authenticationId = user.authenticationId,
            name = user.name,
            generation = user.generation,
            email = user.email,
            phoneNumber = user.phoneNumber,
            snsUrl = user.snsUrl,
            profileUrl = profileUrl
        )

        userRepository.save(userUpdatedProfileUrl)
    }

    override fun deleteMyUserInfoUseCase(authenticationId: Long): User {
        val user = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("user를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        userRepository.delete(user)
        return user
    }

    override fun queryMyUserInfo(authenticationId: Long): UserInfoDto {
        val user = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        return UserInfoDto(
            name = user.name,
            generation = user.generation,
            email = user.email,
            phoneNumber = user.phoneNumber,
            snsUrl = user.snsUrl,
            profileUrl = user.profileUrl
        )
    }

}
