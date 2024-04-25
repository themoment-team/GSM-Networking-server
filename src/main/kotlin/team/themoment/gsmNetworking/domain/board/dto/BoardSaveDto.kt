package team.themoment.gsmNetworking.domain.board.dto

import io.micrometer.core.lang.Nullable
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class BoardSaveDto (
    @field:NotBlank
    @field:Size(max = 50)
    val title: String,
    @field:NotBlank
    @field:Size(max = 1000)
    val content: String,
    @field:NotNull
    @Enumerated(EnumType.STRING)
    val boardCategory: BoardCategory,
    @field:Nullable
    @field:Size(min = 1, max = 30)
    val popupExp: Int?
)
