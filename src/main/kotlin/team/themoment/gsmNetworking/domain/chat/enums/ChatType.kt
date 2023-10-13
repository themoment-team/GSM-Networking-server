package team.themoment.gsmNetworking.domain.chat.enums

import team.themoment.gsmNetworking.domain.chat.domain.BaseChat
import team.themoment.gsmNetworking.domain.chat.domain.SystemChat
import team.themoment.gsmNetworking.domain.chat.domain.UserChat


enum class ChatType(val clazz: Class<out BaseChat>) {
    ALL(BaseChat::class.java),
    USER(UserChat::class.java),
    SYSTEM(SystemChat::class.java);

    class Alias {
        companion object {
            const val USER = "1"
            const val SYSTEM = "2"
        }
    }
}
