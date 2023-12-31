package team.themoment.gsmNetworking.global.exception.handler

import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import team.themoment.gsmNetworking.common.exception.StompException
import team.themoment.gsmNetworking.common.socket.sender.StompSender


// TODO 이거 지금 딱히 사용 안되는데 지우고 나중에 필요할 떄 다시 구현하기
@RestControllerAdvice
class GlobalSocketExceptionHandler(
    private val stompSender: StompSender
) {
    private val log = LoggerFactory.getLogger(this.javaClass)!! //TODO 로깅 구현 통일하기

    /**
     * [StompException]의 구현체를 핸들링하는 메서드 입니다.
     *
     * @param e [StompException]의 구현체
     */
    @MessageExceptionHandler(StompException::class)
    fun handler(e: StompException) {
        log.warn("StompException Occur ", e)
        stompSender.sendErrorMessage(e, e.path)
    }

    /**
     * 예외처리하지 않은 에러를 처리합니다.
     *
     * @param e [RuntimeException]또는 [RuntimeException]을 상속하는 객체
     */
    @MessageExceptionHandler(Exception::class)
    fun handler(e: Exception): Unit = log.error("Uncatched Exception Occur ", e)

}
