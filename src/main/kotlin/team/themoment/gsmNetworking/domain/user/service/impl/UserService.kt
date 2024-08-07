package team.themoment.gsmNetworking.domain.user.service.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.auth.domain.Authority
import team.themoment.gsmNetworking.domain.auth.repository.AuthenticationRepository
import team.themoment.gsmNetworking.domain.mentee.repository.MenteeRepository
import team.themoment.gsmNetworking.domain.user.domain.User
import team.themoment.gsmNetworking.domain.user.dto.*
import team.themoment.gsmNetworking.domain.user.repository.UserRepository
import team.themoment.gsmNetworking.domain.user.service.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val menteeRepository: MenteeRepository,
    private val authenticationRepository: AuthenticationRepository
) : GenerateUserUseCase,
    ModifyUserInfoByIdUseCase,
    GenerateProfileUrlUseCase,
    DeleteUserInfoByIdUseCase,
    QueryUserInfoByIdUseCase,
    QueryUserInfoByUserIdUseCase,
    QueryEmailByUserIdUseCase,
    QueryUserIsTeacherUsecase,
    UpdateUserProfileNumberUseCase,
    QueryUserByIdUseCase {

    override fun queryUserById(authenticationId: Long): User {
        val user = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("user를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        return user
    }

    @Transactional
    override fun generateUser(userSaveInfoDto: UserSaveInfoDto, authenticationId: Long): User {
        if(userRepository.existsByAuthenticationId(authenticationId)){
            val user = queryUserById(authenticationId)

            checkExistUserByInfo(userSaveInfoDto, user)

            val userToUpdate = User(
                user.id,
                authenticationId = authenticationId,
                name = userSaveInfoDto.name,
                generation = userSaveInfoDto.generation,
                email = userSaveInfoDto.email,
                phoneNumber = userSaveInfoDto.phoneNumber,
                snsUrl = userSaveInfoDto.snsUrl,
                profileUrl = null
            )
            menteeRepository.deleteByUser(user)
            return userRepository.save(userToUpdate)
        }

        validateExistUserByPhoneNumber(userSaveInfoDto.phoneNumber);
        validateExistUserByEmail(userSaveInfoDto.email)

        val userToSave = User(
            authenticationId = authenticationId,
            name = userSaveInfoDto.name,
            generation = userSaveInfoDto.generation,
            email = userSaveInfoDto.email,
            phoneNumber = userSaveInfoDto.phoneNumber,
            snsUrl = userSaveInfoDto.snsUrl,
            profileUrl = null
        )
        return userRepository.save(userToSave)
    }

    @Transactional
    override fun modifyUserInfoById(authenticationId: Long, userSaveInfoDto: UserSaveInfoDto) {
        val user = queryUserById(authenticationId)

        checkExistUserByInfo(userSaveInfoDto, user)

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

    private fun checkExistUserByInfo(userSaveInfoDto: UserSaveInfoDto, user: User) {
        if (userSaveInfoDto.phoneNumber != user.phoneNumber)
            validateExistUserByPhoneNumber(userSaveInfoDto.phoneNumber)
        else if (userSaveInfoDto.email != user.email)
            validateExistUserByEmail(userSaveInfoDto.email)
    }

    private fun validateExistUserByPhoneNumber(phoneNumber: String) {
        if (userRepository.existsByPhoneNumber(phoneNumber))
            throw ExpectedException("이미 사용중인 핸드폰 번호입니다.", HttpStatus.BAD_REQUEST)
    }

    private fun validateExistUserByEmail(email: String) {
        if (userRepository.existsByEmail(email))
            throw ExpectedException("이미 사용중인 이메일입니다.", HttpStatus.BAD_REQUEST)
    }

    @Transactional
    override fun generateProfileUrl(authenticationId: Long, dto: ProfileUrlRegistrationDto) {
        val user = queryUserById(authenticationId)

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
            profileUrl = profileUrl,
            defaultImgNumber = user.defaultImgNumber,
            boards = user.boards,
            comments = user.comments,
            likes = user.likes
        )

        userRepository.save(userUpdatedProfileUrl)
    }

    @Transactional
    override fun deleteUserInfoByIdUseCase(authenticationId: Long): User {
        val user = queryUserById(authenticationId)

        userRepository.delete(user)
        return user
    }

    @Transactional(readOnly = true)
    override fun queryUserInfoById(authenticationId: Long): UserInfoDto {
        val user = queryUserById(authenticationId)

        return UserInfoDto(
            id = user.id,
            name = user.name,
            generation = user.generation,
            email = user.email,
            phoneNumber = user.phoneNumber,
            snsUrl = user.snsUrl,
            profileUrl = user.profileUrl,
            defaultImgNumber = user.defaultImgNumber
        )
    }

    @Transactional(readOnly = true)
    override fun queryUserInfoByUserId(userId: Long): UserSimpleInfoDto {
        val user = userRepository.findById(userId)
            .orElseThrow { ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND) }

        return UserSimpleInfoDto(
            id = user.id,
            name = user.name,
            generation = user.generation,
            profileUrl = user.profileUrl
        )
    }

    @Transactional(readOnly = true)
    override fun queryEmailByUserId(email: String): UserIdDto {
        val user = userRepository.findByEmail(email)
            ?: throw ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        return UserIdDto(
            userId = user.id
        )
    }

    @Transactional(readOnly = true)
    override fun queryUserIsTeacher(authenticationId: Long): UserIsTeacherDto {
        val authentication = authenticationRepository.findById(authenticationId)
            .orElseThrow { throw ExpectedException("유저의 권한 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND) }

        return UserIsTeacherDto(isTeacher = authentication.authority == Authority.TEACHER)

    }

    @Transactional
    override fun updateUserProfileNumber(userProfileNumberDto: UserProfileNumberDto, authenticationId: Long) {
        val user = queryUserById(authenticationId)

        user.updateProfileNumber(userProfileNumberDto.profileNumber)
        userRepository.save(user)
    }
}
