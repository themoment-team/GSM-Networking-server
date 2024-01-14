package team.themoment.gsmNetworking.domain.mentee.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.mentee.domain.Mentee
import team.themoment.gsmNetworking.domain.mentee.dto.MenteeRegistrationDto
import team.themoment.gsmNetworking.domain.mentee.repository.MenteeRepository
import team.themoment.gsmNetworking.domain.mentee.service.GenerateMenteeUseCase
import team.themoment.gsmNetworking.domain.user.dto.UserSaveInfoDto
import team.themoment.gsmNetworking.domain.user.service.GenerateUserUseCase

@Service
class MenteeService(
    private val menteeRepository: MenteeRepository,
    private val generateUserUseCase: GenerateUserUseCase,
) : GenerateMenteeUseCase {

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
}
