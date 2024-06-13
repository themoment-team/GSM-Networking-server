package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.MentorCompanyAddressListDto

interface QueryAllMentorCompanyAddressUseCase {
    fun queryAllMentorCompanyAddress(dto: List<MentorCompanyAddressListDto>)
}