package team.themoment.gsmNetworking.domain.user.dto

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UserProfileNumberDto (
    @NotNull
    @Size(min = 0, max = 6)
    val profileNumber: Int
)