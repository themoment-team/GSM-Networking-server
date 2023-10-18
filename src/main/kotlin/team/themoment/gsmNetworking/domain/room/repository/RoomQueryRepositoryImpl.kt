package team.themoment.gsmNetworking.domain.room.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import team.themoment.gsmNetworking.domain.chat.domain.QBaseChat
import team.themoment.gsmNetworking.domain.chat.domain.QBaseChat.*
import team.themoment.gsmNetworking.domain.room.domain.QRoomUser.roomUser
import team.themoment.gsmNetworking.domain.room.domain.RoomUser
import team.themoment.gsmNetworking.domain.room.dto.domain.RoomUserDto

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
            .orderBy(roomUser.lastViewedTime.desc())
            .fetchFirst()
    }

    /**
     * 사용자의 최근 업데이트된 방 목록을 페이지네이션하여 조회합니다.
     *
     * @param userId 방 목록을 조회할 사용자의 고유 식별자
     * @param pageable 페이지 정보 (페이지 번호, 페이지 크기 등)
     * @return 방 목록과 페이지에 대한 정보
     */
    override fun findRoomsRecentUpdated(userId: Long, pageable: Pageable): Page<RoomUserDto> {
        val rooms = findRoomsByUserId(userId, pageable)
        val total = findCountUserRooms(userId)

        return PageImpl(rooms, pageable, total)
    }

    /**
     * 특정 사용자의 최근 채팅을 기준으로 방 목록을 페이지네이션하여 조회합니다.
     *
     * @param userId 방 목록을 조회할 사용자의 고유 식별자
     * @param pageable 페이지 정보 (페이지 번호, 페이지 크기 등)
     * @return 방 목록
     */
    private fun findRoomsByUserId(userId: Long, pageable: Pageable): List<RoomUserDto> {
        val subBaseChat = QBaseChat("subBaseChat")
        val getResentChatIdSubQuery = JPAExpressions
            .select(subBaseChat.id)
            .from(subBaseChat)
            .where(subBaseChat.room.id.eq(roomUser.room.id))
            .orderBy(subBaseChat.createAt.desc(), subBaseChat.id.desc())
            .limit(1)

        return queryFactory
            .select(
                Projections.constructor(
                    RoomUserDto::class.java,
                    roomUser.userId,
                    roomUser.room.id,
                    roomUser.roomName,
                    roomUser.lastViewedTime
                )
            )
            .from(roomUser)
            .innerJoin(baseChat)
            .on(
                roomUser.userId.eq(userId),
                baseChat.room.id.eq(getResentChatIdSubQuery),
                baseChat.room.id.eq(roomUser.room.id)
            )
            .orderBy(baseChat.createAt.desc(), baseChat.id.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
    }

    /**
     * 특정 사용자의 방 개수를 조회합니다.
     *
     * @param userId 방 개수를 조회할 사용자의 고유 식별자
     * @return 해당 사용자의 방 개수
     */
    private fun findCountUserRooms(userId: Long): Long {
        return queryFactory
            .select(roomUser.count())
            .from(roomUser)
            .where(roomUser.userId.eq(userId))
            .fetchFirst() // fetchFirst or fetchOne - 집계함수를 사용하기 때문에 무조건 1 개의 row를 반환한다는 점에선 fetchOne이 맞는거 같은데, 잘 모르겠음 이 경우 nullable 처리를 어떻게 해야 하나? 잘 모르겠음
    }
}
