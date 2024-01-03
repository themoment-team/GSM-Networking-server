package team.themoment.gsmNetworking.domain.user.service

import team.themoment.gsmNetworking.domain.user.domain.User
import team.themoment.gsmNetworking.domain.user.dto.UserRegistrationDto

interface GenerateUserUseCase {

    fun generateUser(dto: UserRegistrationDto, authenticationId: Long): User
}
