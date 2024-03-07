package team.themoment.gsmNetworking.domain.user.service

import team.themoment.gsmNetworking.domain.user.dto.UserSaveInfoDto

interface ModifyUserInfoByIdUseCase {

    fun modifyUserInfoById(authenticationId: Long, userSaveInfoDto: UserSaveInfoDto)
}
