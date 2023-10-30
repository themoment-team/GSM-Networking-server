package team.themoment.gsmNetworking.common.util

class StompPathUtil {
    companion object {
        const val PREFIX_TO_ROOM = "/topic/room"
        // /queue/user/{sessionId} 로 요청 보냄
        const val PREFIX_TO_USER = "/queue/user"

        fun getAllPrefix(): List<String> {
            return listOf(PREFIX_TO_ROOM, PREFIX_TO_USER)
        }
    }
}
