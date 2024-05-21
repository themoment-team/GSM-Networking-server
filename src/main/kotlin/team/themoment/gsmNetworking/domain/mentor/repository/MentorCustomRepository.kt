package team.themoment.gsmNetworking.domain.mentor.repository

import team.themoment.gsmNetworking.domain.mentor.dto.MentorEmailDto
import team.themoment.gsmNetworking.domain.mentor.dto.MentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.MyMentorInfoDto

interface MentorCustomRepository {

    fun findAllMentorInfoDto(): List<MentorInfoDto>

    fun findMyMentorInfoDto(authenticationId: Long): MyMentorInfoDto?

    fun findAllMentorEmailDto(): List<MentorEmailDto>
}
