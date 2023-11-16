package team.themoment.gsmNetworking.common.socket.message

enum class GlobalMessageCode(
    private val _supportClass: Class<out Any>
) : MessageCode {
    ERROR(StompMessage::class.java);

    override fun code(): String {
        return name
    }

    override fun isSupportClass(clazz: Class<out Any>): Boolean {
        return this._supportClass.isAssignableFrom(clazz)
    }

    override fun supportClass(): Class<out Any> {
        return this._supportClass
    }
}
