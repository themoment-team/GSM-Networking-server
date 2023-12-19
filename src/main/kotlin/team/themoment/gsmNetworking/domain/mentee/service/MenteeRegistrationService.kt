package team.themoment.gsmNetworking.domain.mentee.service

import org.springframework.stereotype.Service
import team.themoment.gsmNetworking.domain.mentee.domain.Mentee
import team.themoment.gsmNetworking.domain.mentee.dto.MenteeRegistrationDto
import team.themoment.gsmNetworking.domain.mentee.repository.MenteeRepository
import team.themoment.gsmNetworking.domain.user.dto.UserRegistrationDto
import team.themoment.gsmNetworking.domain.user.service.UserRegistrationService

@Service
class MenteeRegistrationService(
    private val userRegistrationService: UserRegistrationService,
    private val menteeRepository: MenteeRepository
) {

    fun execute(menteeRegistrationDto: MenteeRegistrationDto) {
        val userRegistrationDto = UserRegistrationDto(
            menteeRegistrationDto.name,
            menteeRegistrationDto.generation,
            menteeRegistrationDto.phoneNumber,
            menteeRegistrationDto.email,
            null,
            menteeRegistrationDto.profileUrl
        )
        val user = userRegistrationService.execute(userRegistrationDto)
        val mentee = Mentee(user)

        menteeRepository.save(mentee)
    }
}