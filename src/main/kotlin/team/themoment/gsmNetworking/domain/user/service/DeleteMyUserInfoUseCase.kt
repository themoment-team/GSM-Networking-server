package team.themoment.gsmNetworking.domain.user.service

import team.themoment.gsmNetworking.domain.user.domain.User

interface DeleteMyUserInfoUseCase {

    fun deleteMyUserInfoUseCase(authenticationId: Long): User
}
