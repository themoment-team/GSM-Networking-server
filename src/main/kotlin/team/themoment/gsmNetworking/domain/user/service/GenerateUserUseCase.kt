package team.themoment.gsmNetworking.domain.user.service

import team.themoment.gsmNetworking.domain.user.domain.User
import team.themoment.gsmNetworking.domain.user.dto.UserSaveInfoDto

interface GenerateUserUseCase {

    fun generateUser(dto: UserSaveInfoDto, authenticationId: Long): User
}
