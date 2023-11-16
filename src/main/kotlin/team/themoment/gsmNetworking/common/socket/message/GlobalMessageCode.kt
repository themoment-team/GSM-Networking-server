package team.themoment.gsmNetworking.common.socket.message

enum class GlobalMessageCode : MessageCode {
    ERROR;

    override fun code(): String {
        return name
    }
}
