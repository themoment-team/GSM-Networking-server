package team.themoment.gsmNetworking.domain.message.controller

import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.socket.sender.StompSender
import team.themoment.gsmNetworking.common.exception.StompException
import team.themoment.gsmNetworking.common.exception.model.ErrorCode
import team.themoment.gsmNetworking.common.socket.message.StompMessage
import team.themoment.gsmNetworking.common.util.StompPathUtil
import team.themoment.gsmNetworking.common.util.UUIDUtils
import team.themoment.gsmNetworking.domain.message.dto.api.code.MessageMessageCode
import team.themoment.gsmNetworking.domain.message.dto.api.req.*
import team.themoment.gsmNetworking.domain.message.dto.api.res.*
import team.themoment.gsmNetworking.domain.message.service.*
import java.security.Principal
import java.time.Instant

@RestController
class MessageWebsocketController(
    private val stompSender: StompSender,
    private val saveMessageService: SaveMessageService,
    private val checkMessageService: CheckMessageService,
    private val queryHeadersService: QueryHeadersService,
    private val queryMessagesService: QueryMessagesService,
) {
    @MessageMapping("/message")
    fun sendMessage(
        @Payload req: SaveMessageReq,
        principal: Principal,
        @Header("simpSessionId") sessionId: String
    ) {
        val from = principal.name.toLong()
        try {
            val savedMessageDto = saveMessageService.saveMessage(req.to, from, req.message)

            stompSender.sendMessage(
                StompMessage(savedMessageDto, MessageMessageCode.MESSAGE),
                "${StompPathUtil.PREFIX_TOPIC_MESSAGE_HEADER}/${savedMessageDto.user1Id}-${savedMessageDto.user2Id}"
            )
            val messageOccurRes = MessageOccurRes(
                savedMessageDto.user1Id,
                savedMessageDto.user2Id,
                UUIDUtils.getEpochMilli(savedMessageDto.messageId)
            )
            stompSender.sendMessageToUser(StompMessage(messageOccurRes, MessageMessageCode.MESSAGE_OCCUR), from)
            stompSender.sendMessageToUser(StompMessage(messageOccurRes, MessageMessageCode.MESSAGE_OCCUR), req.to)

        } catch (ex: RuntimeException) {
            stompSender.sendErrorMessageToSession(
                StompException(
                    code = ErrorCode.BAD_REQUEST,
                    message = ex.message.orEmpty(),
                    path = "${StompPathUtil.PREFIX_QUEUE_USER}/${sessionId}"
                ), sessionId
            )
        }
    }

    @MessageMapping("/check")
    fun checkMessage(
        @Payload req: CheckMessageReq,
        principal: Principal,
        @Header("simpSessionId") sessionId: String
    ) {
        val from = principal.name.toLong()
        try {
            checkMessageService.checkMessage(req.to, from, Instant.ofEpochMilli(req.epochMilli))

            val user1Id = minOf(req.to, from)
            val user2Id = maxOf(req.to, from)

            stompSender.sendMessage(
                StompMessage(CheckMessageRes(from, req.epochMilli), MessageMessageCode.MESSAGE_CHECKED),
                "${StompPathUtil.PREFIX_TOPIC_MESSAGE_HEADER}/${user1Id}-${user2Id}"
            )

        } catch (ex: RuntimeException) {
            stompSender.sendErrorMessageToSession(
                StompException(
                    code = ErrorCode.BAD_REQUEST,
                    message = ex.message.orEmpty(),
                    path = "${StompPathUtil.PREFIX_QUEUE_USER}/${sessionId}"
                ), sessionId
            )
        }
    }


    @MessageMapping("/query/header")
    fun queryHeader(
        @Payload req: QueryHeadersReq,
        principal: Principal,
        @Header("simpSessionId") sessionId: String
    ) {
        val userId = principal.name.toLong()
        try {
            val headerResponses =
                queryHeadersService.getMessageInfosByUserId(userId, Instant.ofEpochMilli(req.epochMilli), req.limit)

            stompSender.sendMessageToSession(
                StompMessage(HeadersRes(headerResponses), MessageMessageCode.HEADERS),
                sessionId
            )

        } catch (ex: RuntimeException) {
            stompSender.sendErrorMessageToSession(
                StompException(
                    code = ErrorCode.BAD_REQUEST,
                    message = ex.message.orEmpty(),
                    path = "${StompPathUtil.PREFIX_QUEUE_USER}/${sessionId}"
                ), sessionId
            )
        }
    }

    @MessageMapping("/query/message")
    fun queryMessage(
        @Payload req: QueryMessagesReq,
        @Header("simpSessionId") sessionId: String
    ) {
        try {
            val messageResponses = queryMessagesService.getMessagesBetweenUsers(
                req.user1Id,
                req.user2Id,
                Instant.ofEpochMilli(req.epochMilli),
                req.limit,
                req.direction
            )

            stompSender.sendMessageToSession(
                StompMessage(MessagesRes(messageResponses), MessageMessageCode.MESSAGES),
                sessionId
            )

        } catch (ex: RuntimeException) {
            stompSender.sendErrorMessageToSession(
                StompException(
                    code = ErrorCode.BAD_REQUEST,
                    message = ex.message.orEmpty(),
                    path = "${StompPathUtil.PREFIX_QUEUE_USER}/${sessionId}"
                ), sessionId
            )
        }
    }
}
