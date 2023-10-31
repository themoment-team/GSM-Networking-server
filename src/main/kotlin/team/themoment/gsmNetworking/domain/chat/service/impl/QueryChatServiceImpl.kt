package team.themoment.gsmNetworking.domain.chat.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.chat.dto.domain.BaseChatDto
import team.themoment.gsmNetworking.domain.chat.dto.ws.request.QueryChatRequest
import team.themoment.gsmNetworking.domain.chat.dto.ws.request.QueryRecentChatRequest
import team.themoment.gsmNetworking.domain.chat.repository.ChatRepository
import team.themoment.gsmNetworking.domain.chat.service.QueryChatService
import team.themoment.gsmNetworking.domain.room.repository.RoomRepository
import java.lang.IllegalArgumentException
import java.time.Instant

@Service
class QueryChatServiceImpl(
    private val roomRepository: RoomRepository,
    private val chatRepository: ChatRepository
) : QueryChatService {

    @Transactional(readOnly = true)
    override fun chatsByTimeAndDirection(req: QueryChatRequest): List<BaseChatDto> {
        validateRoomId(req.roomId)
        return chatRepository.findChatsByTimeAndDirection(req.roomId, req.direction, Instant.ofEpochMilli(req.time), req.limit)
    }

    @Transactional(readOnly = true)
    override fun recentChats(req: QueryRecentChatRequest): List<BaseChatDto> {
        validateRoomId(req.roomId)
        return chatRepository.findRecentChats(req.roomId, req.limit)
    }

    private fun validateRoomId(roomId: Long) {
        if (!roomRepository.existsById(roomId)) {
            throw IllegalArgumentException("유효하지 않은 roomId 입니다. roomId가 $roomId 인 Room을 찾을 수 없습니다.")
        }
    }
}
