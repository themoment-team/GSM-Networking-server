package team.themoment.gsmNetworking.domain.chat.repository

import team.themoment.gsmNetworking.domain.chat.dto.domain.BaseChatDto
import team.themoment.gsmNetworking.domain.chat.enums.Direction
import java.time.Instant

/**
 * [Chat]에 대한 쿼리 기능을 제공하는 리포지토리입니다.
 */
interface ChatQueryRepository {

    /**
     * 특정 방에서 지정된 방향과 시간을 기준으로 채팅 메시지를 검색합니다.
     *
     * ```
     * time param 값에 따라 방향이 결정
     *  - [Direction.UP]인 경우 지정된 시간 이전의 채팅을 검색 (요청에 사용된 시간 포함)
     *  - [Direction.DOWN]인 경우 지정된 시간 이후의 채팅을 검색 (요청에 사용된 시간 포함)
     * ```
     *
     * @param roomId 채팅 메시지를 검색할 방의 고유 식별자.
     * @param direction 채팅 메시지를 검색할 방향.
     * @param time 검색의 기준이 되는 시간.
     * @param limit 최대 반환 개수.
     * @return 조건에 맞는 채팅 메시지 목록. 시간순으로 내림차순으로 정렬되어 반환됩니다.
     */
    fun findChatsByTimeAndDirection(roomId: Long, direction: Direction, time: Instant, limit: Long): List<BaseChatDto>

    /**
     * 특정 방에서 최근 채팅 메시지를 검색합니다.
     *
     * @param roomId 채팅 메시지를 검색할 방의 고유 식별자.
     * @param limit 최대 반환 개수.
     * @return 최근 채팅 메시지 목록. 시간순으로 내림차순으로 정렬되어 반환됩니다.
     */
    fun findRecentChats(roomId: Long, limit: Long): List<BaseChatDto>
}
