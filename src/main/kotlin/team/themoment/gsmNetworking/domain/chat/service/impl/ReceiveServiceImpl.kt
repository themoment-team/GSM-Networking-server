package team.themoment.gsmNetworking.domain.chat.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.chat.domain.BaseChat
import team.themoment.gsmNetworking.domain.chat.dto.ws.request.UserChatRequest
import team.themoment.gsmNetworking.domain.chat.dto.ws.response.UserChatServiceRs
import team.themoment.gsmNetworking.domain.chat.mapper.ChatMapper
import team.themoment.gsmNetworking.domain.chat.repository.ChatRepository
import team.themoment.gsmNetworking.domain.chat.service.ReceiveService
import team.themoment.gsmNetworking.domain.room.domain.Room
import team.themoment.gsmNetworking.domain.room.domain.RoomUser
import team.themoment.gsmNetworking.domain.room.repository.*
import java.lang.IllegalArgumentException

@Service
class ReceiveServiceImpl(
    private val roomRepository: RoomRepository,
    private val chatRepository: ChatRepository,
    private val roomUserRepository: RoomUserRepository,
) : ReceiveService {

    @Transactional
    override fun execute(userId: Long, req: UserChatRequest): UserChatServiceRs {
        val room = getRoom(req.roomId)
        val savedChat = saveChat(room, userId, req)
        val updatedRoomUsers = updateRoomUsers(room, userId, savedChat)
        return UserChatServiceRs(savedChat, updatedRoomUsers)
    }

    private fun getRoom(roomId: Long): Room {
        return roomRepository.findById(roomId)
            .orElseThrow { IllegalArgumentException("유효하지 않은 roomId 입니다. roomId가 $roomId 인 Room을 찾을 수 없습니다.") }
    }

    private fun saveChat(room: Room, userId: Long, req: UserChatRequest): BaseChat {
        return chatRepository.save(
            ChatMapper.userChatRequestToChat(
                room = room,
                senderId = userId,
                req = req
            )
        )
    }

    private fun updateRoomUsers(room: Room, userId: Long, savedChat: BaseChat): List<RoomUser> {
        val updatedRoomUsers = room.roomUsers.map { roomUser ->
            roomUser.refresh(
                lastViewedChatId = if (roomUser.userId == userId) savedChat.id else roomUser.lastViewedChatId,
                recentChatId = savedChat.id
            )
        }
        roomUserRepository.saveAll(updatedRoomUsers)
        return updatedRoomUsers
    }

}
