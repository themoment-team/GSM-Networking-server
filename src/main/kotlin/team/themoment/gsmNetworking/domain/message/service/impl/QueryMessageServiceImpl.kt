package team.themoment.gsmNetworking.domain.message.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.util.UUIDUtils
import team.themoment.gsmNetworking.domain.message.dto.api.res.HeaderRes
import team.themoment.gsmNetworking.domain.message.dto.api.res.MessageRes
import team.themoment.gsmNetworking.domain.message.dto.domain.HeaderDto
import team.themoment.gsmNetworking.domain.message.enums.QueryDirection
import team.themoment.gsmNetworking.domain.message.repository.MessageRepository
import team.themoment.gsmNetworking.domain.message.service.QueryHeaderService
import team.themoment.gsmNetworking.domain.message.service.QueryHeadersService
import team.themoment.gsmNetworking.domain.message.service.QueryMessagesService
import team.themoment.gsmNetworking.domain.user.repository.UserRepository
import java.time.Instant

/**
 * Message 도메인과 관련된 Query Service 인터페이스의 구현체.
 */
@Service
class QueryMessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository
) : QueryHeadersService, QueryHeaderService, QueryMessagesService {

    @Transactional(readOnly = true)
    override fun getHeaderByUserIds(user1Id: Long, user2Id: Long): HeaderDto? {
        val (user1Id, user2Id) = if (user1Id < user2Id) user1Id to user2Id else user2Id to user1Id
        if (!userRepository.existsById(user1Id)) {
            throw IllegalArgumentException("사용자 ID가 $user1Id 인 사용자를 찾을 수 없습니다")
        }
        if (!userRepository.existsById(user2Id)) {
            throw IllegalArgumentException("사용자 ID가 $user2Id 인 사용자를 찾을 수 없습니다")
        }
        return messageRepository.findHeaderBetweenUsers(user1Id, user2Id)
            ?.let { HeaderDto(it.id, it.user1Id, it.user2Id, it.recentMessageId) }
    }

    @Transactional(readOnly = true)
    override fun getMessageInfosByUserId(userId: Long, time: Instant, limit: Long): List<HeaderRes> {
        if (!userRepository.existsById(userId)) {
            throw IllegalArgumentException("사용자 ID가 $userId 인 사용자를 찾을 수 없습니다")
        }

        val messageMetaDtos = messageRepository.findMessageInfosByUserId(userId, time, limit)

        return messageMetaDtos.map {
            val opponentUserId = findOtherUserId(userId, it.user1Id, it.user2Id)

            HeaderRes(
                it.recentMessageId,
                opponentUserId,
                UUIDUtils.getInstant(it.recentMessageId),
                isCheckedMessage(UUIDUtils.getEpochMilli(it.recentMessageId), it.lastViewedEpochMilli)
            )
        }
    }

    @Transactional(readOnly = true)
    override fun getMessagesBetweenUsers(
        user1Id: Long,
        user2Id: Long,
        time: Instant,
        limit: Long,
        direction: QueryDirection
    ): List<MessageRes> {
        val (user1Id, user2Id) = if (user1Id < user2Id) user1Id to user2Id else user2Id to user1Id //TODO 그냥 invalid 예외 던지는게 더 나을듯

        val userMessageInfoPair = messageRepository.findPairUserMessageInfoBetweenUsers(user1Id, user2Id)
            ?: throw IllegalArgumentException("유효하지 않은 UserId. $user1Id 와 $user2Id 사이의 메시지 정보를 찾을 수 없습니다.")

        val user1MessageInfo = userMessageInfoPair.first
        val user2MessageInfo = userMessageInfoPair.second

        return messageRepository.findMessagesBetweenUsers(user1Id, user2Id, time, limit, direction).map {
            MessageRes(
                messageId = it.messageId,
                user1Id = it.user1Id,
                user2Id = it.user2Id,
                direction = it.direction,
                content = it.content,
                messageTime = UUIDUtils.getEpochMilli(it.messageId),
                isCheckedUser1 = isCheckedMessage(
                    user1MessageInfo.lastViewedEpochMilli,
                    UUIDUtils.getEpochMilli(it.messageId)
                ),
                isCheckedUser2 = isCheckedMessage(
                    user2MessageInfo.lastViewedEpochMilli,
                    UUIDUtils.getEpochMilli(it.messageId)
                )
            )
        }
    }

    private fun findOtherUserId(myUserId: Long, userId1: Long, userId2: Long): Long =
        when (myUserId) {
            userId1 -> userId2
            userId2 -> userId1
            else -> throw RuntimeException("내 아이디($myUserId)가 아이디1($userId1)과 아이디2($userId2) 중 어느 것과도 일치하지 않습니다.")
        }

    private fun isCheckedMessage(lastViewedMessageId: Long, messageId: Long): Boolean {
        return lastViewedMessageId >= messageId
    }
}
