package team.themoment.gsmNetworking.domain.chat.controller

import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.exception.model.ErrorCode
import team.themoment.gsmNetworking.common.socket.message.StompMessage
import team.themoment.gsmNetworking.common.util.StompPathUtil
import team.themoment.gsmNetworking.domain.chat.domain.BaseChat
import team.themoment.gsmNetworking.domain.chat.dto.domain.BaseChatDto
import team.themoment.gsmNetworking.domain.chat.dto.ws.request.QueryChatRequest
import team.themoment.gsmNetworking.domain.chat.dto.ws.request.QueryRecentChatRequest
import team.themoment.gsmNetworking.domain.chat.dto.ws.request.UserChatRequest
import team.themoment.gsmNetworking.domain.chat.dto.ws.response.ChatResponse
import team.themoment.gsmNetworking.domain.chat.exception.ChatStompException
import team.themoment.gsmNetworking.domain.chat.mapper.ChatMapper
import team.themoment.gsmNetworking.domain.chat.mapper.RoomMapper
import team.themoment.gsmNetworking.domain.chat.sender.ChatStompSender
import team.themoment.gsmNetworking.domain.chat.service.QueryChatService
import team.themoment.gsmNetworking.domain.chat.service.ReceiveService
import team.themoment.gsmNetworking.domain.room.domain.RoomUser
import team.themoment.gsmNetworking.domain.room.dto.ws.RoomUserResponse
import java.lang.IllegalArgumentException
import java.security.Principal

@RestController
class ChatMessageController(
    private val receiveService: ReceiveService,
    private val queryChatService: QueryChatService,
    private val chatStompSender: ChatStompSender
) {

    @MessageMapping("/chat")
    fun sendMessage(
        @Payload userChatRequest: UserChatRequest,
        principal: Principal,
        @Header("simpSessionId") sessionId: String
    ) {
        try {
            val rs = receiveService.execute(principal.name.toLong(), userChatRequest)

            sendChatToRoomUsers(rs.savedChat)
            sendUpdatedRoomInfoToUsers(rs.savedChat, rs.updatedRoomUsers)

        } catch (ex: IllegalArgumentException) {
            throw ChatStompException(
                code = ErrorCode.BAD_REQUEST,
                message = "존재하지 않는 Room 입니다. id: ${userChatRequest.roomId}",
                sessionId = sessionId,
                path = "${StompPathUtil.PREFIX_TO_USER}/${sessionId}"
            )
        }
    }

    @MessageMapping("/query/recent-chats")
    fun queryRecentChat(
        @Payload queryRecentChatRequest: QueryRecentChatRequest,
        @Header("simpSessionId") sessionId: String
    ) {
        try {
            val rs = queryChatService.recentChats(queryRecentChatRequest)

            sendChatsToSession(rs, sessionId)

        } catch (ex: IllegalArgumentException) {
            throw ChatStompException(
                code = ErrorCode.BAD_REQUEST,
                message = "존재하지 않는 Room 입니다. id: ${queryRecentChatRequest.roomId}",
                sessionId = sessionId,
                path = "${StompPathUtil.PREFIX_TO_USER}/${sessionId}"
            )
        }
    }

    @MessageMapping("/query/chats")
    fun queryChat(
        @Payload queryChatRequest: QueryChatRequest,
        @Header("simpSessionId") sessionId: String
    ) {
        try {
            val rs = queryChatService.chatsByTimeAndDirection(queryChatRequest)

            sendChatsToSession(rs, sessionId)

        } catch (ex: IllegalArgumentException) {
            throw ChatStompException(
                code = ErrorCode.BAD_REQUEST,
                message = "존재하지 않는 Room 입니다. id: ${queryChatRequest.roomId}",
                sessionId = sessionId,
                path = "${StompPathUtil.PREFIX_TO_USER}/${sessionId}"
            )
        }
    }

    private fun sendChatToRoomUsers(savedChat: BaseChat) {
        val ms: ChatResponse = ChatMapper.chatToChatResponse(savedChat)
        val path = "${StompPathUtil.PREFIX_TO_ROOM}/${savedChat.room.id}"
        chatStompSender.sendMessage(StompMessage(ms), path)
    }

    private fun sendChatsToSession(chats: List<BaseChatDto>, sessionId: String) {
        chatStompSender.sendMessageToSession(StompMessage(chats), sessionId)
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
