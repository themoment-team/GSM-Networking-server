package team.themoment.gsmNetworking.domain.chat.dto.domain

import org.springframework.data.annotation.CreatedDate
import team.themoment.gsmNetworking.domain.chat.enums.ChatType
import team.themoment.gsmNetworking.domain.room.domain.Room
import java.time.LocalDateTime
import javax.persistence.*

data class BaseChatDto(
    val id: Long,
    val room: Room,
    val content: String,
    val senderId: Long,
    val type: ChatType,
    var createAt: LocalDateTime
)
