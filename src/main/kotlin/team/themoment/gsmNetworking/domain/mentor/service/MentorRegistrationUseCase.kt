package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.MentorSaveInfoDto

interface MentorRegistrationUseCase {

    fun mentorRegistration(dto: MentorSaveInfoDto, authenticationId: Long)
}
