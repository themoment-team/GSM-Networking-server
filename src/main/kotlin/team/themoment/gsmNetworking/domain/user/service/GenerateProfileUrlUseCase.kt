package team.themoment.gsmNetworking.domain.user.service

import team.themoment.gsmNetworking.domain.user.dto.ProfileUrlRegistrationDto

interface GenerateProfileUrlUseCase {

    fun generateProfileUrl(authenticationId: Long, dto: ProfileUrlRegistrationDto)
}
