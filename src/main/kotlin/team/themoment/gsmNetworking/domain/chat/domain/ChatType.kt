package team.themoment.gsmNetworking.domain.chat.domain

enum class ChatType(clazz: Class<out BaseChat>) {
    ALL(BaseChat::class.java),
    USER(UserChat::class.java),
    SYSTEM(SystemChat::class.java)
}
