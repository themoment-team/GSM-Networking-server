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
        val (user1Id, user2Id) = if (fromUserId < toUserId) fromUserId to toUserId else toUserId to fromUserId

        val direction =
            if (user1Id == fromUserId) Message.MessageDirection.ToUser1 else Message.MessageDirection.ToUser2

        val header = messageRepository.findHeaderBetweenUsers(user1Id, user2Id)

        val newMessageId = UUIDUtils.generateUUIDv7()

        val savedMessage = if (header != null) {
            saveMessage(newMessageId, header, direction, message)
        } else {
            saveFirstMessage(newMessageId, user1Id, user2Id, direction, message)
        }

        return createMessageDto(savedMessage)
    }

    // 기존에 채팅이 존재함, header를 갱신하고, 새로운 message를 저장함
    private fun saveMessage(
        newMessageId: UUID,
        header: Header,
        direction: Message.MessageDirection,
        content: String
    ): Message {
        val refreshedHeader = headerRepository.save(header.copyWithNewRecentChatId(header, newMessageId))
        val newMessage = Message(
            messageId = newMessageId,
            header = refreshedHeader,
            direction = direction,
            content = content
        )
        return messageRepository.save(newMessage)
    }

    // 채팅이 처음 시작됨, header, userMessageInfo가 message와 함께 생성됨
    private fun saveFirstMessage(
        newMessageId: UUID,
        user1Id: Long,
        user2Id: Long,
        direction: Message.MessageDirection,
        content: String
    ): Message {
        val newHeader = headerRepository.save(Header(user1Id, user2Id, newMessageId))
        val newMessage = Message(
            messageId = newMessageId,
            header = newHeader,
            direction = direction,
            content = content
        )
        // 둘 다 채팅을 아직 확인하지 않았으므로 기본값이 부여됨
        userMessageInfoRepository.save(UserMessageInfo(userId = user1Id, opponentUserId = user2Id, header = newHeader))
        userMessageInfoRepository.save(UserMessageInfo(userId = user2Id, opponentUserId = user1Id, header = newHeader))

        return messageRepository.save(newMessage)
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
}
