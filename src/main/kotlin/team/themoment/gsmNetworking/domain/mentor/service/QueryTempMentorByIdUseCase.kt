package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorInfoDto

interface QueryTempMentorByIdUseCase {

    fun queryTempMentorById(id: Long): TempMentorInfoDto
}
