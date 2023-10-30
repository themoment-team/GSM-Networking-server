package team.themoment.gsmNetworking.domain.chat.service

import team.themoment.gsmNetworking.domain.chat.dto.ws.request.UserChatRequest
import team.themoment.gsmNetworking.domain.chat.dto.ws.response.UserChatServiceRs

/**
 * 요청한 채팅을 저장하고, 유효한(채팅방에 포함된) 사용자들에게 전달하는 기능을 처리하는 서비스 객체입니다.
 */
interface ReceiveService {
    fun execute(userId: Long, req: UserChatRequest) : UserChatServiceRs
}
