package team.themoment.gsmNetworking.domain.mentee.service

import team.themoment.gsmNetworking.domain.mentee.dto.MenteeInfoDto

interface QueryMenteeInfoByIdUseCase {

    fun queryMenteeInfoById(authenticationId: Long): MenteeInfoDto
}
