package team.themoment.gsmNetworking.domain.message.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.message.repository.UserMessageInfoRepository
import team.themoment.gsmNetworking.domain.message.service.CheckMessageService
import java.time.Instant

@Service
class CheckMessageServiceImpl(
    private val userMessageInfoRepository: UserMessageInfoRepository,
) : CheckMessageService {

    @Transactional
    override fun execute(toUserId: Long, fromUserId: Long, time: Instant) {

        val fromUserMessageInfo = userMessageInfoRepository.findByUserIdAndOpponentUserId(fromUserId, toUserId)
            ?: throw IllegalArgumentException("유효하지 않은 UserId. $fromUserId 와 $toUserId 사이의 메시지를 찾을 수 없습니다.")

        if (time.toEpochMilli() > fromUserMessageInfo.lastViewedEpochMilli) {
            userMessageInfoRepository.save(fromUserMessageInfo.updateLastViewedEpochMilli(time.toEpochMilli()))
        } else {
            //TODO 새로 체크 요청한 시간이 기존에 체크 요청한 시간보다 큰 경우 처리
        }
    }
}
