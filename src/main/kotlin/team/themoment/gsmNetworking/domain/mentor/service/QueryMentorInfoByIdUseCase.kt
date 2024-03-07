package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.MyMentorInfoDto

interface QueryMentorInfoByIdUseCase {

    fun queryMentorInfoById(authenticationId: Long): MyMentorInfoDto
}
