package team.themoment.gsmNetworking.domain.board.dto

import io.micrometer.core.lang.Nullable
import org.springframework.web.multipart.MultipartFile
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.*

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
    @field:Min(1)
    @field:Max(30)
    val popupExp: Int?,
    val files: List<MultipartFile>
)
