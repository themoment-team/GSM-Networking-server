package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.MentorInfoDto

interface QueryAllMentorService {

    fun queryAllMentorExecute(): List<MentorInfoDto>
}