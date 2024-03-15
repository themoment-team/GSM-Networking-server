package team.themoment.gsmNetworking.domain.message.service

import team.themoment.gsmNetworking.domain.message.dto.domain.MessageDto

/**
 * 두 사용자간의 채팅 메시지를 저장하고, 저장된 메시지 정보를 반환하는 역할의 Service.
 */
interface SaveMessageService {
    /**
     * 두 사용자간의 채팅 메시지를 저장하고, 저장된 메시지 정보를 반환합니다.
     *
     * @param toUserId 메시지를 전송하는 사용자의 고유 식별자
     * @param fromUserId 상대방 사용자의 고유 식별자
     * @param message 저장하려는 메시지
     * @return [MessageDto]
     */
    fun saveMessage(toUserId: Long, fromUserId: Long, message: String): MessageDto
}
