package team.themoment.gsmNetworking.domain.mentor.repository

import team.themoment.gsmNetworking.domain.mentor.dto.MentorInfoDto

interface MentorCustomRepository {

    fun findAllMentorInfoDto(): List<MentorInfoDto>

}