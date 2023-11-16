package team.themoment.gsmNetworking.domain.message.dto.api.code

import team.themoment.gsmNetworking.common.socket.message.MessageCode
import team.themoment.gsmNetworking.domain.message.dto.api.res.CheckMessageRes
import team.themoment.gsmNetworking.domain.message.dto.api.res.HeadersRes
import team.themoment.gsmNetworking.domain.message.dto.api.res.MessageOccurRes
import team.themoment.gsmNetworking.domain.message.dto.api.res.MessagesRes
import team.themoment.gsmNetworking.domain.message.dto.domain.MessageDto

enum class MessageMessageCode(
    private val _supportClass: Class<out Any>
) : MessageCode {
    MESSAGE(MessageDto::class.java),
    MESSAGES(MessagesRes::class.java),
    HEADERS(HeadersRes::class.java),
    MESSAGE_OCCUR(MessageOccurRes::class.java),
    MESSAGE_CHECKED(CheckMessageRes::class.java);

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
