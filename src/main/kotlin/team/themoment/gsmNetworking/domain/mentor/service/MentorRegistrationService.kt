package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.MentorRegistrationDto

interface MentorRegistrationService {

    fun mentorRegistrationExecute(dto: MentorRegistrationDto)
}