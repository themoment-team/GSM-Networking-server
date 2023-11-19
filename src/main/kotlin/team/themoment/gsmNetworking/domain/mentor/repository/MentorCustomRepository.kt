package team.themoment.gsmNetworking.domain.mentor.repository

import team.themoment.gsmNetworking.domain.mentor.dto.MentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.ProfileMentorInfoDto

interface MentorCustomRepository {

    fun findAllMentorInfoDto(): List<MentorInfoDto>

    fun findMyMentorInfoDto(authenticationId: Long): ProfileMentorInfoDto?
}
