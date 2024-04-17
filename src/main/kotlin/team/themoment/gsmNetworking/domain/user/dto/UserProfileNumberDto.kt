package team.themoment.gsmNetworking.domain.user.dto

import javax.validation.constraints.NotNull

data class UserProfileNumberDto (
    @NotNull
    val profileNumber: Int
)