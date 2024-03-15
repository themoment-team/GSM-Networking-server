package team.themoment.gsmNetworking.domain.user.service

import team.themoment.gsmNetworking.domain.user.domain.User

interface DeleteUserInfoByIdUseCase {

    fun deleteUserInfoByIdUseCase(authenticationId: Long): User
}
