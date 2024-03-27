package team.themoment.gsmNetworking.domain.user.service

import team.themoment.gsmNetworking.domain.user.dto.UserSimpleInfoDto

interface QueryUserInfoByUserIdUseCase {
    fun queryUserInfoByUserId(userId: Long): UserSimpleInfoDto
}