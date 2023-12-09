package team.themoment.gsmNetworking.domain.message.repository

import team.themoment.gsmNetworking.domain.message.domain.Header
import team.themoment.gsmNetworking.domain.message.domain.Message
import team.themoment.gsmNetworking.domain.message.domain.UserMessageInfo
import team.themoment.gsmNetworking.domain.message.dto.domain.MessageMetaDto
import team.themoment.gsmNetworking.domain.message.dto.domain.MessageDto
import team.themoment.gsmNetworking.domain.message.enums.QueryDirection
import java.time.Instant

/**
 * [Message] 엔티티와 관련된 쿼리 기능을 담당하는 Repository.
 *
 * [Message] 엔티티 외에도 다른 엔티티 정보를 반환하기도 합니다.
 *
 * 역할을 명확히 분리하기 어렵고, 분리하더라도 ~~CustomRepository가 너무 많아지기 때문입니다.
 * 추후 기능이 많아지면 필요에 따라 분리할 수도 있습니다.
 */
interface MessageCustomRepository {

    /**
     * 특정 사용자의 특정 시간 이전에 채팅이 발생한 채팅 정보 목록을 일부 조회합니다.
     *
     * @param userId 사용자의 고유 식별자
     * @param time 필터링에 사용될 시간
     * @param limit 반환할 결과의 개수
     * @return [MessageMetaDto] 목록
     */
    fun findMessageInfosByUserId(userId: Long, time: Instant, limit: Long): List<MessageMetaDto>

    /**
     * 두 사용자 간의 채팅 목록을 가져옵니다. 특정 시간을 기준으로 이전, 이후 데이터를 일부 조회합니다.
     *
     * @param user1Id 사용자1의 고유 식별자
     * @param user2Id 사용자2의 고유 식별자
     * @param time 조회의 기준이 되는 시간
     * @param direction 특정 시간을 기준으로 이전, 이후 데이터를 불러올지 결정하는 값
     * @param limit 반환할 결과의 개수
     * @return [MessageDto] 목록
     */
    fun findMessagesBetweenUsers(
        user1Id: Long,
        user2Id: Long,
        time: Instant,
        limit: Long,
        direction: QueryDirection
    ): List<MessageDto>

    /**
     * 두 사용자 간의 채팅 정보를 가져옵니다.
     *
     * @param user1Id 사용자1의 고유 식별자
     * @param user2Id 사용자2의 고유 식별자
     * @return nullable [Message]
     */
    fun findHeaderBetweenUsers(user1Id: Long, user2Id: Long): Header?

    /**
     * 두 사용자 간의 메시지 정보를 가져옵니다.
     *
     * @param user1Id 사용자1의 고유 식별자
     * @param user2Id 사용자2의 고유 식별자
     * @return Pair<UserMessageInfo, UserMessageInfo> first가 user1, second가 user2 정보를 담고 있습니다.
     */
    fun findPairUserMessageInfoBetweenUsers(user1Id: Long, user2Id: Long): Pair<UserMessageInfo, UserMessageInfo>?
}
