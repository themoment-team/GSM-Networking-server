package team.themoment.gsmNetworking.global.socket.sender

import org.slf4j.LoggerFactory
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.common.socket.message.StompMessage
import team.themoment.gsmNetworking.common.socket.sender.StompSender
import team.themoment.gsmNetworking.common.util.StompPathUtil
import team.themoment.gsmNetworking.global.socket.connect.ConnectedInfoRepository

@Component
class StompSenderImpl(
    private val messageSendingOperations: SimpMessageSendingOperations,
    private val connectedInfoRepository: ConnectedInfoRepository
) : StompSender {

    //TODO 로그 비침투적으로 작성?
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    override fun sendMessage(message: StompMessage<*>, path: String) {
        //TODO 여기도 Connection 확인해야 하나? 근데 그럼 path 인덱스 설정을 추가해야 함
        messageSendingOperations.convertAndSend(path, message)
    }

    override fun sendMessageToUser(message: StompMessage<*>, userId: Long) {
        // PREFIX_TO_USER를 가지는(user queue를 구독하는) 모든 session에 message 보내기
        val subUrls = getSubscriptionUrls(userId)
        if (subUrls.isNotEmpty()) {
            subUrls.forEach { path -> sendMessage(message, path) }
        } else {
            // TODO 알람 보내기, 아직 구현 계획은 없음
            log.debug("User ID가 $userId 인 사용자의 Connection 된 Queue가 존재하지 않습니다. 메시지를 전송하지 않습니다.")
        }
    }

    override fun sendMessageToSession(message: StompMessage<*>, sessionId: String) {
        val path = "${StompPathUtil.PREFIX_QUEUE_USER}/$sessionId"

        // isSessionSubscribedToPath를 쓰면 Apic으로 테스트가 불가능, Apic은 한 session이 한 sub만 가능함
        // TODO 위 문제 해결하고 isSessionSubscribedToPath 사용하게 변경
        if (isSessionSubscribing(sessionId)) {
            sendMessage(message, path)
        } else {
            // 연결이 끊어진 경우는 응답할 필요가 없으므로 스킵
            log.debug("$path Path는 구독 중인 상태가 아닙니다. 메시지를 전송하지 않습니다.")
        }
    }

    private fun getSubscriptionUrls(userId: Long): List<String> {
        return connectedInfoRepository.findByUserId(userId)
            .flatMap { connect -> connect.subscribes.filter { it.subscribeUrl.contains(StompPathUtil.PREFIX_QUEUE_USER) } }
            .map { it.subscribeUrl }
    }

    private fun isSessionSubscribedToPath(sessionId: String, path: String): Boolean {
        val connectedInfo = connectedInfoRepository.findById(sessionId).orElse(null)
        return connectedInfo?.subscribes?.any { it.subscribeUrl == path } ?: false
    }

    private fun isSessionSubscribing(sessionId: String): Boolean {
        val connectedInfo = connectedInfoRepository.findById(sessionId).orElse(null)
        return connectedInfo != null
    }

}
