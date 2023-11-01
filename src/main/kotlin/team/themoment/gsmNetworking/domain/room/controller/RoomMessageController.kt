package team.themoment.gsmNetworking.domain.chat.controller

import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.exception.model.ErrorCode
import team.themoment.gsmNetworking.common.socket.message.StompMessage
import team.themoment.gsmNetworking.common.util.StompPathUtil
import team.themoment.gsmNetworking.domain.chat.dto.ws.request.QueryRecentRoomsRequest
import team.themoment.gsmNetworking.domain.chat.exception.ChatStompException
import team.themoment.gsmNetworking.domain.chat.mapper.RoomMapper
import team.themoment.gsmNetworking.domain.chat.sender.ChatStompSender
import team.themoment.gsmNetworking.domain.room.dto.ws.RoomUserResponse
import team.themoment.gsmNetworking.domain.room.dto.ws.request.QueryRoomsRequest
import team.themoment.gsmNetworking.domain.room.service.QueryRoomService
import java.lang.IllegalArgumentException
import java.security.Principal
import java.time.Instant

@RestController
class RoomMessageController(
    //TODO 굳이 도메인 별로 StompSender 필요 없을 듯? Excepotion은 따로 분기처리하고
    private val chatStompSender: ChatStompSender,
    private val queryRoomService: QueryRoomService
) {

    @MessageMapping("/query/recent-rooms")
    fun queryRecentRooms(
        @Payload req: QueryRecentRoomsRequest,
        principal: Principal,
        @Header("simpSessionId") sessionId: String
    ) {
        try {
            val rs = queryRoomService.recentRooms(req.userId, req.limit)

            sendRoomsToSession(RoomMapper.roomUserDtosToListRoomUserResponses(rs), sessionId)

        } catch (ex: IllegalArgumentException) {
            throw ChatStompException(
                code = ErrorCode.BAD_REQUEST,
                message = "존재하지 않는 User 입니다. id: ${req.userId}",
                sessionId = sessionId,
                path = "${StompPathUtil.PREFIX_TO_USER}/${sessionId}"
            )
        }
    }

    @MessageMapping("/query/rooms")
    fun queryRooms(
        @Payload req: QueryRoomsRequest,
        principal: Principal,
        @Header("simpSessionId") sessionId: String
    ) {
        try {
            val rs = queryRoomService.roomsByTime(req.userId, Instant.ofEpochMilli(req.time), req.limit)

            sendRoomsToSession(RoomMapper.roomUserDtosToListRoomUserResponses(rs), sessionId)

        } catch (ex: IllegalArgumentException) {
            throw ChatStompException(
                code = ErrorCode.BAD_REQUEST,
                message = "존재하지 않는 User 입니다. id: ${req.userId}",
                sessionId = sessionId,
                path = "${StompPathUtil.PREFIX_TO_USER}/${sessionId}"
            )
        }
    }

    private fun sendRoomsToSession(chats: List<RoomUserResponse>, sessionId: String) {
        chatStompSender.sendMessageToSession(StompMessage(chats), sessionId)
    }
}
