package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.MentorInfoDto

interface QueryAllMentorsService {

    fun queryAllMentorsExecute(): List<MentorInfoDto>
}