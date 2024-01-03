package team.themoment.gsmNetworking.domain.mentee.service

import team.themoment.gsmNetworking.domain.mentee.dto.MenteeRegistrationDto

interface GenerateMenteeUseCase {

    fun generateMentee(menteeRegistrationDto: MenteeRegistrationDto, authenticationId: Long)
}