package team.themoment.gsmNetworking.domain.board.dto

import io.micrometer.core.lang.Nullable
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.*

data class BoardUpdateDto (
    @field:NotBlank
    @field:Size(max = 50)
    val title: String,
    @field:NotBlank
    @field:Size(max = 1000)
    val content: String,
    @field:NotNull
    @Enumerated(EnumType.STRING)
    val boardCategory: BoardCategory
)