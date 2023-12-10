package team.themoment.gsmNetworking.domain.message.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.message.repository.HeaderRepository
import team.themoment.gsmNetworking.domain.message.repository.MessageRepository
import team.themoment.gsmNetworking.domain.message.repository.UserMessageInfoRepository
import team.themoment.gsmNetworking.domain.message.service.CheckMessageService
import java.time.Instant

@Service
class CheckMessageServiceImpl(
    private val userMessageInfoRepository: UserMessageInfoRepository,
    private val messageRepository: MessageRepository
) : CheckMessageService {

    @Transactional
    override fun execute(toUserId: Long, fromUserId: Long, time: Instant) {
        val (user1Id, user2Id) = if (toUserId < fromUserId) toUserId to fromUserId else fromUserId to toUserId

        val userMessageInfoPair = messageRepository.findPairUserMessageInfoBetweenUsers(user1Id, user2Id)
            ?: throw IllegalArgumentException("유효하지 않은 UserId. $fromUserId 와 $toUserId 사이의 메시지 찾을 수 없습니다.")

        val fromUserMessageInfo =
            if (userMessageInfoPair.first.userId == fromUserId) userMessageInfoPair.first
            else userMessageInfoPair.second

        if (time.toEpochMilli() > fromUserMessageInfo.lastViewedEpochMilli) {
            userMessageInfoRepository.save(fromUserMessageInfo.updateLastViewedEpochMilli(time.toEpochMilli()))
        } else {
            //TODO 새로 체크 요청한 시간이 기존에 체크 요청한 시간보다 큰 경우 처리
        }
    }
}
