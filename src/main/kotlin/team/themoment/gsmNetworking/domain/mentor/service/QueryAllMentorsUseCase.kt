package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.MentorInfoDto

interface QueryAllMentorsUseCase {

    fun queryAllMentors() : List<MentorInfoDto>
}
