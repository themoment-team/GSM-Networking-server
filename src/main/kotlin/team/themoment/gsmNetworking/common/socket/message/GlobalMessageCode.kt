package team.themoment.gsmNetworking.common.socket.message

import team.themoment.gsmNetworking.common.socket.model.StompErrorResponse

/**
 * 전역적으로 사용되는 message code를 정의하는 [MessageCode]의 구현체.
 */
enum class GlobalMessageCode(
    private val supportClass: Class<out Any>
) : MessageCode {

    /**
     * 일반적인 에러 메시지를 반환할 때 사용되는 Message Code.
     *
     * [StompErrorResponse] 또는 [StompErrorResponse]의 자식 객체와 매핑되어 사용된다.
     */
    ERROR(StompErrorResponse::class.java);

    override val code: String
        get() = name

    override fun isSupportClass(clazz: Class<out Any>): Boolean {
        return this.supportClass.isAssignableFrom(clazz)
    }

    override fun getSupportClass(): Class<out Any> {
        return this.supportClass
    }
}
