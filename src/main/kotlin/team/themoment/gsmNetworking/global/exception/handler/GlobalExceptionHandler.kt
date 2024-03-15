package team.themoment.gsmNetworking.global.exception.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.multipart.MaxUploadSizeExceededException
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.common.exception.model.ExceptionResponse
import team.themoment.gsmNetworking.global.filter.LoggingFilter

private val log = LoggerFactory.getLogger(LoggingFilter::class.java)!!

/**
 * 전역적으로 예외 클래스를 핸들링 하는 클래스 입니다.
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * ExpectedException과 ExpectedException을 상속받은 예외 클래스를 핸들링하는 메서드 입니다.
     *
     * @param e ExpectedException 혹은 ExpectedException을 상속하는 클래스
     * @return ResponseEntity
     */
    @ExceptionHandler(ExpectedException::class)
    fun handler(e: ExpectedException): ResponseEntity<ExceptionResponse> {
        log.warn("ExpectedException : ${e.message}")
        log.trace("ExpectedException Details : $e")
        return ResponseEntity
            .status(e.status)
            .body(ExceptionResponse(message = e.message))
    }

    /**
     * 예외처리 되지 않은 RuntimeException을 포함한 하위 에러를 핸들링하는 메서드 입니다.
     *
     * @param e RuntimException 혹은 RuntimeException을 상속하는 클래스
     * @return ResponseEntity
     */
    @ExceptionHandler(RuntimeException::class)
    fun unExpectedExceptionHandler(e: RuntimeException): ResponseEntity<ExceptionResponse> {
        log.error("UnExpectedException Details : $e")
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ExceptionResponse(message = "알 수 없는 문제가 발생하였습니다."))
    }

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun maxUploadSizeExceededExceptionHandler(e: MaxUploadSizeExceededException): ResponseEntity<ExceptionResponse> {
        log.warn("MaxUploadSizeExceededException : ${e.message}")
        log.trace("MaxUploadSizeExceededException Details : $e")
        return ResponseEntity
            .status(HttpStatus.PAYLOAD_TOO_LARGE)
            .body(ExceptionResponse(message = "파일 크기는 5MB를 넘을 수 없습니다."))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<ExceptionResponse> {
        log.warn("MethodArgumentNotValidException : ${e.message}")
        log.trace("MethodArgumentNotValidException Details : $e")
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ExceptionResponse(message = methodArgumentNotValidExceptionToJson(e)))
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun methodArgumentTypeMismatchExceptionHandler(e: MethodArgumentTypeMismatchException): ResponseEntity<ExceptionResponse> {
        log.warn("MethodArgumentTypeMismatchException : ${e.message}")
        log.trace("MethodArgumentTypeMismatchException Details : $e")
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ExceptionResponse(message = "잘못된 타입의 인자로 요청을 하였습니다."))
    }

    private fun methodArgumentNotValidExceptionToJson(e: MethodArgumentNotValidException): String {
        val globalResults: MutableMap<String, Any?> = HashMap()
        val fieldResults: MutableMap<String, String?> = HashMap()

        e.bindingResult.globalErrors.forEach { error ->
            globalResults[e.bindingResult.objectName] = error.defaultMessage
        }
        e.bindingResult.fieldErrors.forEach { error ->
            fieldResults[error.field] = error.defaultMessage
        }
        globalResults[e.bindingResult.objectName] = fieldResults

        return ObjectMapper()
            .writeValueAsString(globalResults)
            .replace("\"", "'")
    }
}
