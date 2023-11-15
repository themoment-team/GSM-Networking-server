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
        val (user1Id, user2Id) = if (fromUserId < toUserId) fromUserId to toUserId else toUserId to fromUserId // TODO 로직 중복

        val direction =
            if (user1Id == fromUserId) Message.MessageDirection.ToUser1 else Message.MessageDirection.ToUser2

        val header = messageRepository.findHeaderBetweenUsers(user1Id, user2Id)
        val newMessageId = UUIDUtils.generateUUIDv7()

        if (header != null) {
            val savedMessage = saveMessage(newMessageId, header, direction, message)

            // 메시지를 보내는 사람은 메시지를 본 대상으로 판단
            val userMessageInfo = userMessageInfoRepository.findByUserId(fromUserId)
                ?: throw IllegalArgumentException("userId가 ${fromUserId}인 사용자를 찾을 수 없습니다")
            userMessageInfoRepository.save(
                UserMessageInfo(
                    userMessageInfo.userMessageInfoId,
                    userMessageInfo.userId,
                    userMessageInfo.opponentUserId,
                    newMessageId
                )
            )

            return MessageDto(
                savedMessage.messageId,
                savedMessage.user1Id,
                savedMessage.user2Id,
                savedMessage.direction,
                savedMessage.content
            )
        } else {
            val savedMessage = saveNewMessage(newMessageId, user1Id, user2Id, direction, message)

            // 새로운 Header, UserMessageInfo 생성
            // 메시지를 보내는 사람은 메시지를 본 대상으로 판단
            userMessageInfoRepository.save(
                UserMessageInfo(
                    userId = fromUserId,
                    opponentUserId = toUserId,
                    lastViewedMessageId = newMessageId
                )
            )

            // 다른 사용자의 새로운 Header, UserMessageInfo 생성
            userMessageInfoRepository.save(
                UserMessageInfo(
                    userId = toUserId,
                    opponentUserId = fromUserId,
                    lastViewedMessageId = null
                )
            )

            return MessageDto(
                savedMessage.messageId,
                savedMessage.user1Id,
                savedMessage.user2Id,
                savedMessage.direction,
                savedMessage.content
            )
        }

    }

    private fun saveMessage(
        newMessageId: UUID,
        header: Header,
        direction: Message.MessageDirection,
        content: String
    ): Message {
        val refreshedHeader = header.copyWithNewRecentChatId(header, newMessageId)
        val newMessage = Message(
            messageId = newMessageId,
            header = refreshedHeader,
            direction = direction,
            content = content
        )
        headerRepository.save(refreshedHeader)
        return messageRepository.save(newMessage)
    }

    private fun saveNewMessage(
        newMessageId: UUID,
        user1Id: Long,
        user2Id: Long,
        direction: Message.MessageDirection,
        content: String
    ): Message {
        val newHeader = Header(user1Id, user2Id, newMessageId)
        val newMessage = Message(
            messageId = newMessageId,
            header = newHeader,
            direction = direction,
            content = content
        )
        headerRepository.save(newHeader)
        return messageRepository.save(newMessage)
    }
}
