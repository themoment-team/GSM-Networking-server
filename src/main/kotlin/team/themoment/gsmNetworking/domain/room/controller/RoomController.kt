package team.themoment.gsmNetworking.domain.room.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.common.socket.message.StompMessage
import team.themoment.gsmNetworking.common.util.StompPathUtil
import team.themoment.gsmNetworking.domain.chat.domain.BaseChat
import team.themoment.gsmNetworking.domain.chat.dto.ws.response.ChatResponse
import team.themoment.gsmNetworking.domain.chat.mapper.ChatMapper
import team.themoment.gsmNetworking.domain.chat.mapper.RoomMapper
import team.themoment.gsmNetworking.domain.chat.sender.ChatStompSender
import team.themoment.gsmNetworking.domain.room.domain.Room
import team.themoment.gsmNetworking.domain.room.domain.RoomUser
import team.themoment.gsmNetworking.domain.room.dto.api.RoomRegistrationDto
import team.themoment.gsmNetworking.domain.room.dto.domain.FetchRoomDto
import team.themoment.gsmNetworking.domain.room.dto.ws.RoomUserResponse
import team.themoment.gsmNetworking.domain.room.service.CreateRoomService
import team.themoment.gsmNetworking.domain.room.service.QueryRoomService

@RestController
@RequestMapping("api/v1/room")
class RoomController(
    private val queryRoomService: QueryRoomService,
    private val createRoomService: CreateRoomService,
    private val chatStompSender: ChatStompSender
) {
    @PostMapping("")
    fun createRoom(@RequestBody req: RoomRegistrationDto): ResponseEntity<Room> {
        try {
            val rs = createRoomService.execute(req.user1Id, req.user2Id)

            // TODO 굳이 채팅 방까지 문자 보낼 필요가 있을까?
            sendChatToRoomUsers(rs.savedChat)
            sendUpdatedRoomInfoToUsers(rs.savedChat, rs.updatedRoomUsers)
        } catch (ex: IllegalArgumentException) {
            throw ExpectedException(ex.message.orEmpty(), HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/by-user-ids")
    fun findRoomByUserIds(
        @RequestParam(name = "user1Id", required = true) user1Id: Long,
        @RequestParam(name = "user2Id", required = true) user2Id: Long
    ): ResponseEntity<FetchRoomDto> {
        val rs: FetchRoomDto? = queryRoomService.findRoomByUserIds(user1Id, user2Id)
        return rs?.let { ResponseEntity.ok(it) } ?: ResponseEntity.noContent().build()
    }

    private fun sendChatToRoomUsers(savedChat: BaseChat) {
        val ms: ChatResponse = ChatMapper.chatToChatResponse(savedChat)
        val path = "${StompPathUtil.PREFIX_TO_ROOM}/${savedChat.room.id}"
        chatStompSender.sendMessage(StompMessage(ms), path)
    }

    private fun sendUpdatedRoomInfoToUsers(
        savedChat: BaseChat,
        newRoomUsers: List<RoomUser>
    ) {
        newRoomUsers.forEach {
            val message: RoomUserResponse = RoomMapper.chatAndRoomUserToRoomUserResponse(savedChat, it)
            chatStompSender.sendMessageToUser(
                StompMessage(message),
                it.userId
            )
        }
    }
}
