package team.themoment.gsmNetworking.domain.feed.dto

import team.themoment.gsmNetworking.domain.feed.domain.Category
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class FeedSaveDto (
    @field:NotBlank
    @field:Size(max = 50)
    val title: String,
    @field:NotBlank
    @field:Size(max = 100)
    val content: String,
    @field:NotNull
    @Enumerated(EnumType.STRING)
    val category: Category
)