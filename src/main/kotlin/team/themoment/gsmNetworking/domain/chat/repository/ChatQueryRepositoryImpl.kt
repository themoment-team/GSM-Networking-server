package team.themoment.gsmNetworking.domain.chat.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import team.themoment.gsmNetworking.common.util.UUIDUtils
import team.themoment.gsmNetworking.domain.chat.domain.QBaseChat.baseChat
import team.themoment.gsmNetworking.domain.chat.dto.domain.BaseChatDto
import team.themoment.gsmNetworking.domain.chat.enums.Direction
import java.time.*

/**
 * Chat 쿼리 관련 레포지토리 [ChatQueryRepository]의 구현 클래스입니다.
 */
class ChatQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ChatQueryRepository {

    /**
     * 특정 방에서 지정된 방향과 시간을 기준으로 채팅 메시지를 검색합니다.
     *
     * ```
     * time param 값에 따라 방향이 결정
     * [Direction.UP]인 경우 지정된 시간 **이전**의 Chat를 검색 (저징된 시간 포함)
     * [Direction.DOWN]인 경우 지정된 시간 **이후**의 Chat를 검색 (저징된 시간 포함)
     * ```
     *
     * @param roomId 채팅 메시지를 검색할 방의 고유 식별자.
     * @param direction 채팅 메시지를 검색할 방향.
     * @param time 검색의 기준이 되는 시간.
     * @param limit 최대 반환 개수.
     * @return 조건에 맞는 채팅 메시지 목록. 시간순으로 내림차순으로 정렬되어 반환.
     */
    override fun findChatsByTimeAndDirection(
        roomId: Long,
        direction: Direction,
        time: Instant,
        limit: Long
    ): List<BaseChatDto> {
        val timeCondition = when (direction) {
            // 입력받은 시간 + 1을 미만 조건으로 검색
            Direction.UP -> baseChat.id.lt(UUIDUtils.generateSmallestUUIDv7(time.plusMillis(1)))
            Direction.DOWN -> baseChat.id.goe(UUIDUtils.generateSmallestUUIDv7(time))
        }

        return queryFactory
            .select(
                Projections.constructor(
                    BaseChatDto::class.java,
                    baseChat.id,
                    Projections.constructor(
                        BaseChatDto.RoomInfo::class.java,
                        baseChat.room.id
                    ),
                    baseChat.content,
                    baseChat.senderId,
                    baseChat.type
                )
            )
            .from(baseChat)
            .where(baseChat.room.id.eq(roomId).and(timeCondition))
            .orderBy(baseChat.id.desc())
            .limit(limit)
            .fetch()
    }

    /**
     * 특정 방에서 최근 채팅 메시지를 검색합니다.
     *
     * @param roomId 채팅 메시지를 검색할 방의 고유 식별자.
     * @param limit 최대 반환 개수.
     * @return 최근 채팅 메시지 목록. 시간순으로 내림차순으로 정렬되어 반환.
     */
    override fun findRecentChats(roomId: Long, limit: Long): List<BaseChatDto> =
        findChatsByTimeAndDirection(roomId, Direction.UP, UUIDUtils.MAX_TIME, limit)
}
