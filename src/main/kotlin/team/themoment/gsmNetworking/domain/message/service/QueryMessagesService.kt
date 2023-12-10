package team.themoment.gsmNetworking.domain.message.service

import team.themoment.gsmNetworking.domain.message.dto.api.res.MessageRes
import team.themoment.gsmNetworking.domain.message.enums.QueryDirection
import java.time.Instant

/**
 * 두 사용자 사이의 채팅 문자 목록을 특정 시간 이전/이후로 가져오는 역할의 Service.
 */
interface QueryMessagesService {

    /**
     * 두 사용자 사이의 채팅 문자 목록을 특정 시간 이전/이후로 가져옵니다.
     *
     * @param user1Id 사용자1의 고유 식별자
     * @param user2Id 사용자2의 고유 식별자
     * @param time 결과의 기준이 되는 시간
     * @param limit 결과 목록의 길이 제한
     * @param direction 특정 time 이전/이후
     * @return [List]<[MessageRes]>
     */
    fun getMessagesBetweenUsers(
        user1Id: Long,
        user2Id: Long,
        time: Instant,
        limit: Long,
        direction: QueryDirection
    ): List<MessageRes>

}
