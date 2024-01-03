package team.themoment.gsmNetworking.domain.mentee.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.mentee.domain.Mentee
import team.themoment.gsmNetworking.domain.mentee.dto.MenteeRegistrationDto
import team.themoment.gsmNetworking.domain.mentee.repository.MenteeRepository
import team.themoment.gsmNetworking.domain.user.dto.UserSaveInfoDto
import team.themoment.gsmNetworking.domain.user.service.UserRegistrationService

@Service
@Transactional(rollbackFor = [Exception::class])
class MenteeRegistrationService(
    private val userRegistrationService: UserRegistrationService,
    private val menteeRepository: MenteeRepository
) {

    fun execute(menteeRegistrationDto: MenteeRegistrationDto, authenticationId: Long) {
        val userSaveInfoDto = UserSaveInfoDto(
            name = menteeRegistrationDto.name,
            generation = menteeRegistrationDto.generation,
            phoneNumber = menteeRegistrationDto.phoneNumber,
            email = menteeRegistrationDto.email,
            snsUrl = null,
            profileUrl = menteeRegistrationDto.profileUrl
        )
        val user = userRegistrationService.execute(userSaveInfoDto, authenticationId)
        val mentee = Mentee(user)

        menteeRepository.save(mentee)
    }
}
