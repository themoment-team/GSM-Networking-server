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
import team.themoment.gsmNetworking.domain.message.service.CheckMessageService
import team.themoment.gsmNetworking.domain.message.service.QueryMessageService
import team.themoment.gsmNetworking.domain.message.service.SaveMessageService
import java.security.Principal
import java.time.Instant

@RestController
class MessageWebsocketController(
    private val stompSender: StompSender,
    private val saveMessageService: SaveMessageService,
    private val checkMessageService: CheckMessageService,
    private val queryMessageService: QueryMessageService
) {
    @MessageMapping("/message")
    fun sendMessage(
        @Payload req: SaveMessageReq,
        principal: Principal,
        @Header("simpSessionId") sessionId: String
    ) {
        val from = principal.name.toLong()
        try {
            val savedMessageDto = saveMessageService.execute(req.to, from, req.message)

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
            checkMessageService.execute(req.to, from, req.messageId)

            val user1Id = minOf(req.to, from)
            val user2Id = maxOf(req.to, from)

            stompSender.sendMessage(
                StompMessage(CheckMessageRes(req.messageId), MessageMessageCode.MESSAGE_CHECKED),
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
                queryMessageService.getMessageInfosByUserId(userId, Instant.ofEpochMilli(req.epochMilli), req.limit)

            stompSender.sendMessageToSession(StompMessage(headerResponses, MessageMessageCode.HEADERS), sessionId)

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
            val messageResponses = queryMessageService.getMessagesBetweenUsers(
                req.user1Id,
                req.user2Id,
                Instant.ofEpochMilli(req.epochMilli),
                req.limit,
                req.direction
            )

            stompSender.sendMessageToSession(StompMessage(messageResponses, MessageMessageCode.MESSAGES), sessionId)

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
