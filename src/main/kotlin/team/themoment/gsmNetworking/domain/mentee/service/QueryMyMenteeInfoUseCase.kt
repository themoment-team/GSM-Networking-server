package team.themoment.gsmNetworking.domain.mentee.service

import team.themoment.gsmNetworking.domain.mentee.dto.MenteeInfoDto

interface QueryMyMenteeInfoUseCase {

    fun queryMyMenteeInfo(authenticationId: Long): MenteeInfoDto
}
