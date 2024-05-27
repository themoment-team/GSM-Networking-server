package team.themoment.gsmNetworking.domain.user.service

import team.themoment.gsmNetworking.domain.user.domain.User

interface QueryUserByIdUseCase {

    fun queryUserById(authenticationId: Long): User
}