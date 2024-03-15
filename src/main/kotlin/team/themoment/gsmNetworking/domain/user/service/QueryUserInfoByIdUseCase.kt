package team.themoment.gsmNetworking.domain.user.service

import team.themoment.gsmNetworking.domain.user.dto.UserInfoDto

interface QueryUserInfoByIdUseCase {

    fun queryUserInfoById(authenticationId: Long): UserInfoDto
}
