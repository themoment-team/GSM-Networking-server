package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.MentorSaveInfoDto

interface ModifyMyMentorInfoUseCase {

    fun modifyMyMentorInfo(authenticationId: Long, mentorUpdateInfoDto: MentorSaveInfoDto)
}
