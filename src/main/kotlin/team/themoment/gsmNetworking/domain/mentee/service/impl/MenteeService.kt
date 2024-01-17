package team.themoment.gsmNetworking.domain.mentee.service.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.mentee.domain.Mentee
import team.themoment.gsmNetworking.domain.mentee.dto.MenteeRegistrationDto
import team.themoment.gsmNetworking.domain.mentee.repository.MenteeRepository
import team.themoment.gsmNetworking.domain.mentee.service.DeleteMyMenteeInfoUseCase
import team.themoment.gsmNetworking.domain.mentee.service.GenerateMenteeUseCase
import team.themoment.gsmNetworking.domain.user.dto.UserSaveInfoDto
import team.themoment.gsmNetworking.domain.user.repository.UserRepository
import team.themoment.gsmNetworking.domain.user.service.DeleteMyUserInfoUseCase
import team.themoment.gsmNetworking.domain.user.service.GenerateUserUseCase

@Service
class MenteeService(
    private val menteeRepository: MenteeRepository,
    private val userRepository: UserRepository,
    private val generateUserUseCase: GenerateUserUseCase,
    private val deleteMyUserInfoUseCase: DeleteMyUserInfoUseCase
) : GenerateMenteeUseCase,
    DeleteMyMenteeInfoUseCase {

    @Transactional(rollbackFor = [Exception::class])
    override fun generateMentee(menteeRegistrationDto: MenteeRegistrationDto, authenticationId: Long) {
        val userSaveInfoDto = UserSaveInfoDto(
            name = menteeRegistrationDto.name,
            generation = menteeRegistrationDto.generation,
            phoneNumber = menteeRegistrationDto.phoneNumber,
            email = menteeRegistrationDto.email,
            snsUrl = null
        )
        val user = generateUserUseCase.generateUser(userSaveInfoDto, authenticationId)
        val mentee = Mentee(user = user)

        menteeRepository.save(mentee)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteMyMenteeInfo(authenticationId: Long) {
        val user = deleteMyUserInfoUseCase.deleteMyUserInfoUseCase(authenticationId)

        menteeRepository.deleteByUser(user)
    }
}
