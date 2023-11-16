package team.themoment.gsmNetworking.common.socket.message

/**
 * STOMP 메시지를 사용자에게 전달하기 위한 Wrapper 객체입니다.
 */
class StompMessage<T>(
    val content: T,
    private val _messageCode: MessageCode,
    val messageType: MessageType = MessageType.MESSAGE
) {
    enum class MessageType {
        ERROR,
        MESSAGE
    }

    val messageCode: String
        get() = _messageCode.code()
}

