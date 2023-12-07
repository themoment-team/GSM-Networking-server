package team.themoment.gsmNetworking.domain.message.service

import team.themoment.gsmNetworking.domain.message.dto.domain.HeaderDto
import team.themoment.gsmNetworking.domain.message.dto.api.res.HeaderRes
import team.themoment.gsmNetworking.domain.message.dto.api.res.MessageRes
import team.themoment.gsmNetworking.domain.message.enums.QueryDirection
import java.time.Instant

/**
 * Message 도메인과 관련된 쿼리 성 메서드를 관리하는 Service.
 */
interface QueryMessageService {

    /**
     * 두 사용자 사이의 채팅 메타데이터를 반환합니다.
     *
     * @param user1Id 사용자1의 고유 식별자
     * @param user2Id 사용자2의 고유 식별자
     * @return nullable [HeaderDto], 두 사용자간의 채팅 기록이 없는 경우 null 반환
     */
    fun getHeaderByUserIds(user1Id: Long, user2Id: Long): HeaderDto?

    /**
     * 한 사용자의 채팅 목록을 특정 시간을 기준으로 가져옵니다.
     *
     * @param userId 사용자의 고유 식별자
     * @param time 결과의 기준이 되는 시간
     * @param limit 결과 목록의 길이 제한
     * @return [List]<[HeaderRes]>
     */
    fun getMessageInfosByUserId(userId: Long, time: Instant, limit: Long): List<HeaderRes>

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
