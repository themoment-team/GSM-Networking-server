package team.themoment.gsmNetworking.domain.mentor.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class MentorSaveInfoDto(
    @JsonProperty(required = true)
    val id: Long,

    @field:NotBlank
    val name: String,

    @field:NotNull
    val generation: Int,

    @field:NotBlank
    val phoneNumber: String,

    @field:NotBlank
    val email: String,

    val snsUrl: String?,

    val profileUrl: String?,

    @field:NotEmpty
    val career: List<MentorCareerDto>
)
