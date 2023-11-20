package team.themoment.gsmNetworking.common.socket.message

/**
 * STOMP 메시지를 사용자에게 전달하기 위한 Wrapper 객체입니다.
 */
class StompMessage<T : Any>(
    val content: T,
    private val internalMessageCode: MessageCode,
    val messageType: MessageType = MessageType.MESSAGE
) {
    val messageCode: String
        get() = internalMessageCode.code

    enum class MessageType {
        ERROR,
        MESSAGE
    }

    // TODO 지금은 구현을 못했지만 content만 입력 받아도 적절한 _messageCode를 자동으로 할당되도록 구현했으면 좋겠다.
    //  지금은 임시로 _messageCode가 content Class를 지원하는지 확인하도록 구현하였음
    //  ---
    //  원하는 구현 예시
    //      사용하는 측에서 StompMessage(abcContent) 로만 사용해도 동작하도록
    //      지금은 StompMessage(abcContent, AbcMessageCode.ABC) 이런식으로 사용하는 쪽에서 직접 Type을 지정해 주어여 함
    init {
        require(
            internalMessageCode.isSupportClass(content::class.java)
        ) {
            "사용 된 content의 Type을 지원하지 않는 MessageCode 입니다. " +
                    "올바른 MessageCode를 사용해 주세요. " +
                    "해당 객체 생성자에 사용된 MessageCode, $internalMessageCode 는 ${internalMessageCode.getSupportClass()} 클래스를 지원합니다."
        }
    }
}

