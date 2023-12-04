package team.themoment.gsmNetworking.common.exception

import org.springframework.http.HttpStatus

/**
 * 클라이언트에게 예외 상황에 HttpStatus와 Message를 전달해주기 위해 사용되는 클래스 입니다.
 */
class ExpectedException(
    override val message: String,
    val status: HttpStatus,
) : RuntimeException(message)
