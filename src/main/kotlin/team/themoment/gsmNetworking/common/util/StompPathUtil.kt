package team.themoment.gsmNetworking.common.util

class StompPathUtil {
    companion object {
        const val PREFIX_TOPIC_MESSAGE_HEADER = "/topic/messaging"
        // /queue/user/{sessionId} 로 요청 보낼때 사용
        const val PREFIX_QUEUE_USER = "/queue/user"

        fun getAllPrefix(): List<String> {
            return listOf(PREFIX_TOPIC_MESSAGE_HEADER, PREFIX_QUEUE_USER)
        }
    }
}
