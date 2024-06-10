package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.CompanyAddressRegistrationDto

interface GenerateCompanyAddressUseCase {

    fun generateCompanyAddress(companyAddressRegistrationDto: CompanyAddressRegistrationDto)
}
