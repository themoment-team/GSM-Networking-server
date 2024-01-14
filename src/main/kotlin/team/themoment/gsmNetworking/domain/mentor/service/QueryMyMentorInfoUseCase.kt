package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.MyMentorInfoDto

interface QueryMyMentorInfoUseCase {

    fun queryMyMentorInfo(authenticationId: Long): MyMentorInfoDto
}
