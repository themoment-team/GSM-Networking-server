package team.themoment.gsmNetworking.domain.message.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.util.UUIDUtils
import team.themoment.gsmNetworking.domain.message.domain.Header
import team.themoment.gsmNetworking.domain.message.domain.Message
import team.themoment.gsmNetworking.domain.message.domain.UserMessageInfo
import team.themoment.gsmNetworking.domain.message.dto.domain.MessageDto
import team.themoment.gsmNetworking.domain.message.repository.HeaderRepository
import team.themoment.gsmNetworking.domain.message.repository.MessageRepository
import team.themoment.gsmNetworking.domain.message.repository.UserMessageInfoRepository
import team.themoment.gsmNetworking.domain.message.service.SaveMessageService
import java.util.UUID

@Service
class SaveMessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val userMessageInfoRepository: UserMessageInfoRepository,
    private val headerRepository: HeaderRepository
) : SaveMessageService {

    @Transactional
    override fun execute(toUserId: Long, fromUserId: Long, message: String): MessageDto {
        val (user1Id, user2Id) = getUser1IdAndUser2Id(toUserId, fromUserId)

        val header = messageRepository.findHeaderBetweenUsers(user1Id, user2Id)

        val savedMessage = saveMessage(toUserId, fromUserId, header, message)

        return createMessageDto(savedMessage)
    }

    private fun updateUserMessageInfo(userId: Long, opponentUserId: Long, newMessageId: UUID?) {
        val userMessageInfo = userMessageInfoRepository.findByUserIdAndOpponentUserId(userId, opponentUserId)
            ?: throw IllegalArgumentException("유효하지 않은 UserId. $userId 와 $opponentUserId 사이의 메시지를 찾을 수 없습니다.")

        val updatedInfo = UserMessageInfo(
            userMessageInfo.userMessageInfoId,
            userMessageInfo.userId,
            userMessageInfo.opponentUserId,
            newMessageId ?: userMessageInfo.lastViewedMessageId
        )

        userMessageInfoRepository.save(updatedInfo)
    }

    private fun saveMessage(
        newMessageId: UUID,
        header: Header,
        direction: Message.MessageDirection,
        content: String
    ): Message {
        val refreshedHeader = updateHeader(header, newMessageId)
        val newMessage = Message(
            messageId = newMessageId,
            header = refreshedHeader,
            direction = direction,
            content = content
        )
        return messageRepository.save(newMessage)
    }

    private fun saveFirstMessage(
        newMessageId: UUID,
        user1Id: Long,
        user2Id: Long,
        direction: Message.MessageDirection,
        content: String
    ): Message {
        val newHeader = Header(user1Id, user2Id, newMessageId)
        val newMessage = Message(
            messageId = newMessageId,
            header = updateHeader(newHeader, newMessageId),
            direction = direction,
            content = content
        )
        return messageRepository.save(newMessage)
    }

    private fun updateHeader(header: Header, newMessageId: UUID): Header {
        val refreshedHeader = header.copyWithNewRecentChatId(header, newMessageId)
        headerRepository.save(refreshedHeader)
        return refreshedHeader
    }

    private fun createMessageDto(savedMessage: Message): MessageDto {
        return MessageDto(
            savedMessage.messageId,
            savedMessage.user1Id,
            savedMessage.user2Id,
            savedMessage.direction,
            savedMessage.content
        )
    }

    private fun saveMessage(
        fromUserId: Long,
        toUserId: Long,
        header: Header?,
        message: String
    ): Message {
        val (user1Id, user2Id) = getUser1IdAndUser2Id(toUserId, fromUserId)

        val direction =
            if (user1Id == fromUserId) Message.MessageDirection.ToUser1 else Message.MessageDirection.ToUser2

        val newMessageId = UUIDUtils.generateUUIDv7()

        if (header != null) {
            val savedMessage = saveMessage(newMessageId, header, direction, message)

            updateUserMessageInfo(userId = fromUserId, opponentUserId = toUserId, newMessageId = newMessageId)

            return savedMessage
        } else {
            val savedMessage = saveFirstMessage(newMessageId, user1Id, user2Id, direction, message)

            updateUserMessageInfo(userId = fromUserId, opponentUserId = toUserId, newMessageId = newMessageId)
            updateUserMessageInfo(userId = toUserId, opponentUserId = fromUserId, newMessageId = null)

            return savedMessage
        }
    }

    private fun getUser1IdAndUser2Id(toUserId: Long, fromUserId: Long): Pair<Long, Long> =
        if (fromUserId < toUserId) fromUserId to toUserId else toUserId to fromUserId
}
