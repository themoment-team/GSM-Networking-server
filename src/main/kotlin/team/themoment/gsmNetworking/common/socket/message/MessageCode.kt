package team.themoment.gsmNetworking.common.socket.message

interface MessageCode {

    /**
     * 메시지와 연관된 코드를 가져옵니다. 코드는 구현체 사이의 중복이 발생하지 않는 식별 가능한 값 이여야 합니다.
     *
     * @return 코드
     */
    val code: String

    /**
     * 제공된 클래스가 메시지 코드에서 지원되는지 확인합니다.
     *
     * @param clazz 지원 여부를 확인할 클래스입니다.
     * @return 제공된 클래스가 지원 클래스로부터 할당 가능한 경우 `true`, 그렇지 않은 경우 `false`
     */
    fun isSupportClass(clazz: Class<out Any>): Boolean

    /**
     * 지원하는 클래스 정보를 반환합니다.
     *
     * @return 지원 클래스
     */
    fun getSupportClass(): Class<out Any>
}
