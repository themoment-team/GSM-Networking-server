package team.themoment.gsmNetworking.domain.room.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.chat.domain.SystemChat
import team.themoment.gsmNetworking.domain.chat.dto.ws.response.UserChatServiceRs
import team.themoment.gsmNetworking.domain.chat.repository.ChatRepository
import team.themoment.gsmNetworking.domain.room.domain.Room
import team.themoment.gsmNetworking.domain.room.domain.RoomUser
import team.themoment.gsmNetworking.domain.room.repository.RoomRepository
import team.themoment.gsmNetworking.domain.room.repository.RoomUserRepository
import team.themoment.gsmNetworking.domain.user.domain.User
import team.themoment.gsmNetworking.domain.user.repository.UserRepository
import java.util.UUID

@Service
class CreateRoomServiceImpl(
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository,
    private val roomUserRepository: RoomUserRepository,
    private val chatRepository: ChatRepository
) : CreateRoomService {

    @Transactional
    override fun execute(user1Id: Long, user2Id: Long): UserChatServiceRs {
        val user1 = getUserById(user1Id)
        val user2 = getUserById(user2Id)

        validateRoomNotExist(user1Id, user2Id)

        val initRoom = createNewRoom()
        val initChat = createSystemChat(initRoom)

        val roomUser1 = createRoomUser(user1, user2.name, initChat.id, initChat.id, initRoom)
        val roomUser2 = createRoomUser(user2, user1.name, initChat.id, initChat.id, initRoom)

        val savedRoomUsers = saveRoomUsers(listOf(roomUser1, roomUser2))

        //TODO UserChatServiceRs 이름 바꾸기 -> ChatServiceRs ?
        return UserChatServiceRs(initChat, savedRoomUsers)
    }

    private fun getUserById(userId: Long): User {
        return userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("userId가 $userId 인 사용자를 찾을 수 없습니다") }
    }

    private fun validateRoomNotExist(user1Id: Long, user2Id: Long) {
        if (roomRepository.findRoomByUserIds(user1Id, user2Id) != null) {
            throw IllegalArgumentException("userId가 $user1Id, $user2Id 인 사용자는 이미 채탕방이 존재합니다")
        }
    }

    private fun createNewRoom(): Room {
        return roomRepository.save(Room(roomUsers = emptyList()))
    }

    private fun createSystemChat(room: Room): SystemChat {
        return chatRepository.save(SystemChat(room = room, content = "채팅을 시작합니다."))
    }

    private fun createRoomUser(
        user: User,
        roomName: String,
        lastViewedChatId: UUID,
        recentChatId: UUID,
        room: Room
    ): RoomUser {
        return RoomUser(
            room = room,
            roomName = roomName,
            userId = user.userId,
            lastViewedChatId = lastViewedChatId,
            recentChatId = recentChatId
        )
    }

    private fun saveRoomUsers(roomUsers: List<RoomUser>): List<RoomUser> {
        return roomUserRepository.saveAll(roomUsers).toList()
    }
}
