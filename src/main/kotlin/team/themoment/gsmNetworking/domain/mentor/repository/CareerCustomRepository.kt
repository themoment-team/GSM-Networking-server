package team.themoment.gsmNetworking.domain.mentor.repository

import team.themoment.gsmNetworking.domain.mentor.dto.MentorCompanyAddressListDto

interface CareerCustomRepository {
    fun queryAllCompanyAddress(): List<MentorCompanyAddressListDto>
}
