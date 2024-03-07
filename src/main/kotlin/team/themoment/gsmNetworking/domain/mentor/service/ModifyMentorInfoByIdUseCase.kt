package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.MentorSaveInfoDto

interface ModifyMentorInfoByIdUseCase {

    fun modifyMentorInfoById(authenticationId: Long, mentorUpdateInfoDto: MentorSaveInfoDto)
}
