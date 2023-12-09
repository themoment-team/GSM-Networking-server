package team.themoment.gsmNetworking.domain.message.service

import java.time.Instant

/**
 * 특정 사용자간의 최근 채팅 확인 시간을 갱신하는 역할의 Service.
 */
interface CheckMessageService {
    /**
     * 특정 사용자간의 최근 채팅 확인 시간을 갱신합니다.
     *
     * 만약 저장하려는 최근 채팅의 시간이 기존 시간보다 이전이라면 갱신되지 않습니다.
     *
     * @param toUserId 시간을 갱신하려는 사용자의 고유 식별자
     * @param fromUserId 상대방 사용자의 고유 식별자
     * @param time 변경하려는 시간
     */
    fun execute(toUserId: Long, fromUserId: Long, time: Instant)
}
