package team.themoment.gsmNetworking.domain.message.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.message.domain.Message
import team.themoment.gsmNetworking.domain.message.repository.MessageRepository
import team.themoment.gsmNetworking.domain.message.repository.UserMessageInfoRepository
import team.themoment.gsmNetworking.domain.message.service.CheckMessageService
import java.util.*

@Service
class CheckMessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val userMessageInfoRepository: UserMessageInfoRepository,
) : CheckMessageService {

    @Transactional
    override fun execute(toUserId: Long, fromUserId: Long, messageId: UUID) {
        val message = messageRepository.findById(messageId)
            .orElseThrow { IllegalArgumentException("유효하지 않은 MessageId. MessageId가 $messageId 인 Message를 찾을 수 없습니다.") }

        if (!validMessageByUserIds(toUserId, fromUserId, message)) {
            throw IllegalArgumentException("유효하지 않은 MessageId. $fromUserId 와 $toUserId 사이의 메시지가 아닙니다.")
        }

        val fromUserMessageInfo = userMessageInfoRepository.findByUserIdAndOpponentUserId(fromUserId, toUserId)
            ?: throw IllegalArgumentException("유효하지 않은 UserId. $fromUserId 와 $toUserId 사이의 메시지를 찾을 수 없습니다.")

        userMessageInfoRepository.save(
            fromUserMessageInfo.updateLastViewedMessageId(messageId)
        )
    }

    private fun validMessageByUserIds(toUserId: Long, fromUserId: Long, message: Message): Boolean {
        val user1Id = minOf(toUserId, fromUserId)
        val user2Id = maxOf(toUserId, fromUserId)
        return user1Id == message.user1Id && user2Id == message.user2Id
    }
}
