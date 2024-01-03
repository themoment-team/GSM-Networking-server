package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.MentorRegistrationDto

interface MentorRegistrationUseCase {

    fun mentorRegistration(dto: MentorRegistrationDto, authenticationId: Long)
}
