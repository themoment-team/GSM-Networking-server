package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.SearchTempMentorInfoDto

interface QueryTempMentorsByNameUseCase {

    fun queryTempMentorsByName(name: String): List<SearchTempMentorInfoDto>
}
