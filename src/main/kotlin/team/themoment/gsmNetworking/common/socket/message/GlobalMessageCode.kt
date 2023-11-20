package team.themoment.gsmNetworking.common.socket.message

enum class GlobalMessageCode(
    private val supportClass: Class<out Any>
) : MessageCode {
    ERROR(StompMessage::class.java);

    override val code: String
        get() = name

    override fun isSupportClass(clazz: Class<out Any>): Boolean {
        return this.supportClass.isAssignableFrom(clazz)
    }

    override fun getSupportClass(): Class<out Any> {
        return this.supportClass
    }
}
