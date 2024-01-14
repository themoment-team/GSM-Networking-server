package team.themoment.gsmNetworking.domain.user.service

import team.themoment.gsmNetworking.domain.user.dto.UserSaveInfoDto

interface ModifyMyUserInfoUseCase {

    fun modifyMyUserInfo(authenticationId: Long, userSaveInfoDto: UserSaveInfoDto)
}
