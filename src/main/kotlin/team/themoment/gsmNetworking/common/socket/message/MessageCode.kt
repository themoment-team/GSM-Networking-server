package team.themoment.gsmNetworking.common.socket.message

/**
 * 특정 메시지 DTO와 연관된 message code를 정의하는 역할의 인터페이스.
 *
 * code는 구현체 간에 중복을 피하기 위해 고유하고 식별 가능한 값이어야 합니다.
 *
 * 구체적인 사용 예시를 확인하고 싶다면 [GlobalMessageCode]를 참고하세요.
 */
interface MessageCode {

    /**
     * 메시지와 연관된 code를 가져옵니다.
     *
     * code는 구현체 사이의 중복이 발생하지 않는 식별 가능한 값 이여야 합니다.
     *
     * @return code
     */
    val code: String

    /**
     * 인자로 제공된 class 지원 여부를 반환합니다.
     *
     * @param clazz
     * @return 지원한다면 true, 아니라면 false 반환
     */
    fun isSupportClass(clazz: Class<out Any>): Boolean

    /**
     * 지원하는 클래스 정보를 반환합니다.
     *
     * @return 지원 클래스
     */
    fun getSupportClass(): Class<out Any>
}
