package team.themoment.gsmNetworking.global.exception.handler

import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.common.exception.model.ExceptionResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 전역적으로 예외 클래스를 핸들링 하는 클래스 입니다.
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * ExpectedException과 ExpectedException을 상속받은 예외 클래스를 핸들해는 메서드 입니다.
     *
     * @param e {ExpectedException 혹은 ExpectedException을 상속하는 클래스}
     * @return ExceptionResponse
     */
    @ExceptionHandler(ExpectedException::class)
    fun handler(e: ExpectedException): ResponseEntity<ExceptionResponse> =
        ResponseEntity
            .status(e.status)
            .body(ExceptionResponse(message = e.message))

}