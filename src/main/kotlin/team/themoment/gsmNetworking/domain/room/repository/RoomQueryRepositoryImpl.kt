package team.themoment.gsmNetworking.domain.room.repository

import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.group.GroupBy.list
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import team.themoment.gsmNetworking.common.util.UUIDUtils
import team.themoment.gsmNetworking.domain.chat.domain.QBaseChat.*
import team.themoment.gsmNetworking.domain.room.domain.QRoom.room
import team.themoment.gsmNetworking.domain.room.domain.QRoomUser
import team.themoment.gsmNetworking.domain.room.domain.QRoomUser.roomUser
import team.themoment.gsmNetworking.domain.room.domain.RoomUser
import team.themoment.gsmNetworking.domain.room.dto.domain.FetchRoomDto
import team.themoment.gsmNetworking.domain.room.dto.domain.RoomUserDto
import java.time.Instant

/**
 * Room 쿼리 관련 레포지토리 [RoomQueryRepository]의 구현 클래스입니다.
 */
class RoomQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : RoomQueryRepository {

    /**
     * 특정 사용자의 특정 방에 대한 정보를 검색합니다.
     *
     * @param userId 채팅 정보를 검색할 사용자의 고유 식별자
     * @param roomId 채팅 정보를 검색할 방의 고유 식별자
     * @return 해당 사용자의 해당 방에 대한 정보 (존재하지 않으면 null 반환)
     */
    override fun findRoomUserOrNull(
        userId: Long,
        roomId: Long
    ): RoomUser? {
        return queryFactory
            .select(roomUser)
            .from(roomUser)
            .on(
                roomUser.userId.eq(userId),
                roomUser.room.id.eq(roomId)
            )
            .orderBy(roomUser.lastViewedChatId.desc())
            .fetchFirst()
    }

    /**
     * 특정 사용자의 특정 시간 이전에 채팅이 발생한 방 목록 일부를 조회합니다.
     *
     * @param userId 사용자의 고유 식별자
     * @param time 필터링에 사용될 시간입니다.
     * @param size 반환할 결과의 개수입니다.
     * @return [RoomUserDto] 객체의 리스트입니다.
     */
    override fun findRoomsByTime(userId: Long, time: Instant, size: Int): List<RoomUserDto> =
        findRooms(userId, time, size)

    /**
     * 두 사용자 사이의 채팅방을 가져옵니다.
     *
     * @param user1Id 사용자 1의 식별자
     * @param user2Id 사용자 2의 식별자
     * @return [FetchRoomDto] 반환, 조건을 만족하는 값이 없다면 Null 반환
     */
    override fun findRoomByUserIds(
        user1Id: Long,
        user2Id: Long
    ): FetchRoomDto? {
        // user1가 참여 중인 방 id 목록을 구하는 서브쿼리
        val sub1 = JPAExpressions
            .select(roomUser.room.id)
            .from(roomUser)
            .where(roomUser.userId.eq(user1Id))

        // user2가 참여 중인 방 id 목록을 구하는 서브쿼리
        val sub2 = JPAExpressions
            .select(roomUser.room.id)
            .from(roomUser)
            .where(roomUser.userId.eq(user2Id))

        // 현재 구현 상, 1:1 채팅만 지원하므로 두 사용자가 여러 방을 가질 수 없다.
        // 그래서 해당 서브쿼리의 결과는 1 row 임이 보장되며, 메인 쿼리에서 eq 조건으로 쓰인다.
        val unionSub = JPAExpressions
            .select(room.id)
            .from(room)
            .where(
                room.id.`in`(sub1),
                room.id.`in`(sub2),
            )

        val rs = queryFactory
            .selectFrom(room)
            .where(room.id.eq(unionSub))
            .join(room.roomUsers, roomUser)
            .transform(
                groupBy(room).list(
                    Projections.constructor(
                        FetchRoomDto::class.java,
                        room.id,
                        list(
                            Projections.constructor(
                                FetchRoomDto.RoomUserDto::class.java,
                                roomUser.id,
                                roomUser.userId,
                                roomUser.roomName,
                                roomUser.lastViewedChatId,
                                roomUser.recentChatId
                            )
                        )
                    )
                )
            )

        return if (rs.isEmpty()) {
            null
        } else if (rs.size == 1) {
            rs[0]
        } else {
            throw IllegalStateException("현재 구현 상, 1:1 채팅만 지원하므로 두 사용자가 여러 방을 가질 수 없습니다.")
        }
    }

    /**
     * 특정 사용자의 최근에 채팅이 발생한 방 목록 일부를 조회합니다.
     *
     * @param userId 사용자의 고유 식별자입니다.
     * @param size 반환할 결과의 개수입니다.
     * @return 최근 방을 나타내는 [RoomUserDto] 객체의 리스트입니다.
     */
    override fun findRecentRooms(userId: Long, size: Int): List<RoomUserDto> =
        findRooms(userId, null, size)


    /**
     * 특정 사용자의 특정 시간 이전에 채팅이 발생한 방 목록 일부를 조회합니다.
     *
     * @param userId 사용자의 고유 식별자
     * @param time 시간 (null이면 최근 방 목록 조회)
     * @param size 가져올 방 목록 개수
     * @return 최근 채팅 정보를 포함한 방 목록
     */
    private fun findRooms(userId: Long, time: Instant?, size: Int): List<RoomUserDto> {
        return queryFactory
            .select(
                Projections.constructor(
                    RoomUserDto::class.java,
                    roomUser.id,
                    roomUser.userId,
                    roomUser.room.id,
                    roomUser.roomName,
                    roomUser.lastViewedChatId,
                    Projections.constructor(
                        RoomUserDto.MaxChatInfo::class.java,
                        baseChat.id,
                        baseChat.content,
                        baseChat.senderId,
                        baseChat.type
                    )
                )
            )
            .from(roomUser)
            .innerJoin(baseChat)
            .on(
                roomUser.userId.eq(userId),
                baseChat.id.eq(roomUser.recentChatId),
            )
            .where(
                // 이 부분까지 on 절에 있는게 맞다고 생각하는데, on절에는 null이 들어갈 수 없어서 where 절로 옮김
                recentChatIdLtInstant(time)
            )
            .orderBy(roomUser.userId.asc(), roomUser.recentChatId.desc())
            .limit(size.toLong())
            .fetch()
    }


    /**
     * 특정 사용자의 모든 방 개수를 조회합니다.
     *
     * @param userId 사용자의 고유 식별자
     * @return 해당 사용자의 모든 방 개수
     */
    fun findCountUserRooms(userId: Long): Long {
        return queryFactory
            .select(roomUser.id.count())
            .from(roomUser)
            .where(roomUser.userId.eq(userId))
            .fetchOne()!!
    }

    /**
     * "특정 시간 이전에 채팅이 발생한 RoomUser" 조건을 제공합니다.
     *
     * @param time 특정 시간
     * @return BooleanExpression 조건식. 파라미터가 null이면 null을 반환
     */
    private fun recentChatIdLtInstant(time: Instant?): BooleanExpression? {
        return time?.let { roomUser.recentChatId.lt(UUIDUtils.generateSmallestUUIDv7(time.plusMillis(1))) }
    }

}
