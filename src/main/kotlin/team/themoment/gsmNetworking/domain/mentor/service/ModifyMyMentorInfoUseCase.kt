package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.MentorUpdateInfoDto

interface ModifyMyMentorInfoUseCase {

    fun modifyMyMentorInfo(mentorUpdateInfoDto: MentorUpdateInfoDto)
}
