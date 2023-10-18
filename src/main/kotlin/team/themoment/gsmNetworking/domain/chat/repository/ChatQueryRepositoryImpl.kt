package team.themoment.gsmNetworking.domain.chat.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import team.themoment.gsmNetworking.domain.chat.domain.BaseChat
import team.themoment.gsmNetworking.domain.chat.domain.QBaseChat.baseChat
import team.themoment.gsmNetworking.domain.chat.dto.domain.BaseChatDto
import team.themoment.gsmNetworking.domain.chat.enums.Direction
import team.themoment.gsmNetworking.domain.chat.dto.domain.RecentChatQueryDto
import java.time.LocalDateTime

/**
 * Chat 쿼리 관련 레포지토리 [ChatQueryRepository]의 구현 클래스입니다.
 */
class ChatQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ChatQueryRepository {

    /**
     * 특정 방에서 지정된 방향과 시간을 기준으로 채팅 메시지를 검색합니다.
     *
     * @param roomId 채팅 메시지를 검색할 방의 고유 식별자.
     * @param direction 채팅 메시지를 검색할 방향. [Direction.UP]인 경우 지정된 시간 **이전**의 Chat를 검색, [Direction.DOWN]인 경우 지정된 시간 **이후**의 Chat를 검색.
     * @param time 검색의 기준이 되는 시간.
     * @param limit 최대 반환 개수.
     * @return 조건에 맞는 채팅 메시지 목록. 시간순으로 내림차순으로 정렬되어 반환.
     */
    override fun findChatsByTimeAndDirection(
        roomId: Long,
        direction: Direction,
        time: LocalDateTime,
        limit: Long
    ): List<BaseChat> {
        val timeCondition = when (direction) {
            Direction.UP -> baseChat.createAt.loe(time)
            Direction.DOWN -> baseChat.createAt.goe(time)
        }

        return queryFactory
            .select(baseChat)
            .from(baseChat)
            .where(baseChat.room.id.eq(roomId).and(timeCondition))
            .orderBy(baseChat.createAt.desc(), baseChat.id.desc())
            .limit(limit)
            .fetch()
            .sortedWith(compareBy({ it.createAt }, { it.id }))
    }

    /**
     * 특정 방에서 최근 채팅 메시지를 검색합니다.
     *
     * @param roomId 채팅 메시지를 검색할 방의 고유 식별자.
     * @param limit 최대 반환 개수.
     * @return 최근 채팅 메시지 목록. 시간순으로 내림차순으로 정렬되어 반환.
     */
    override fun findRecentChats(roomId: Long, limit: Long): List<BaseChat> =
        findChatsByTimeAndDirection(roomId, Direction.UP, LocalDateTime.MAX, limit)

    /**
     * 특정 방들의 최근 채팅 메시지를 가져옵니다.
     *
     * @param roomInfos
     * @param limit 최대 반환 개수.
     * @return 최근 채팅 메시지 목록. 시간, ID 순으로 오름차순 정렬되어 반환
     */
    override fun findRoomsRecentChats(roomInfos: List<RecentChatQueryDto>, limit: Long): List<BaseChatDto> {
        return queryFactory
            .select(
                Projections.constructor(
                    BaseChatDto::class.java,
                    baseChat.id,
                    baseChat.room,
                    baseChat.content,
                    baseChat.senderId,
                    baseChat.type,
                    baseChat.createAt
                )
            )
            .from(baseChat)
            .where(baseChat.room.id.`in`(roomInfos.map { it.roomId }))
            .orderBy(baseChat.createAt.desc(), baseChat.createAt.desc())
            .limit(limit)
            .fetch()
    }
}
