package team.themoment.gsmNetworking.domain.user.service

import team.themoment.gsmNetworking.domain.user.dto.UserUpdateInfoDto

interface ModifyMyUserInfoUseCase {

    fun modifyMyUserInfo(authenticationId: Long, userUpdateInfoDto: UserUpdateInfoDto)
}