package team.themoment.gsmNetworking.domain.user.service

import team.themoment.gsmNetworking.domain.user.dto.UserInfoDto

interface QueryMyUserInfoUseCase {

    fun queryMyUserInfo(authenticationId: Long): UserInfoDto
}
