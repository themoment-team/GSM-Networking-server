package team.themoment.gsmNetworking.domain.user.service

import team.themoment.gsmNetworking.domain.user.dto.UserProfileNumberDto

interface UpdateUserProfileNumberUseCase {
    fun updateUserProfileNumber(userProfileNumberDto: UserProfileNumberDto)
}