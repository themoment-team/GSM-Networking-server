package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorInfoDto

interface QueryAllTempMentorsUseCase {

    fun queryAllTempMentors(): List<TempMentorInfoDto>
}
