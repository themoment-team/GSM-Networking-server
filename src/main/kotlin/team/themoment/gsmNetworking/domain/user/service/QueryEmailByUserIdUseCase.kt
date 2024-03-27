package team.themoment.gsmNetworking.domain.user.service

import team.themoment.gsmNetworking.domain.user.dto.UserIdDto

interface QueryEmailByUserIdUseCase {
    fun queryEmailByUserId(email: String): UserIdDto
}