package team.themoment.gsmNetworking.domain.room.repository

import team.themoment.gsmNetworking.domain.room.domain.Room
import team.themoment.gsmNetworking.domain.room.domain.RoomUser
import team.themoment.gsmNetworking.domain.room.dto.domain.FetchRoomDto
import team.themoment.gsmNetworking.domain.room.dto.domain.RoomUserDto
import java.time.Instant

/**
 * [Room], [RoomUser]에 대한 쿼리 기능을 제공하는 리포지토리입니다.
 *
 * [RoomUser]는 [Room]에 의존되는 데이터이며, 주로 함께 사용되기 때문에 [RoomQueryRepository]에서 함께 요청합니다.
 */
interface RoomQueryRepository {

    /**
     * 특정 사용자의 특정 방에 대한 정보를 검색합니다.
     *
     * @param userId 채팅 정보를 검색할 사용자의 고유 식별자
     * @param roomId 채팅 정보를 검색할 방의 고유 식별자
     * @return 해당 사용자의 해당 방에 대한 정보 (존재하지 않으면 null 반환)
     */
    fun findRoomUserOrNull(userId: Long, roomId: Long): RoomUser?

    /**
     * 특정 사용자의 특정 시간 이전에 채팅이 발생한 방 목록 일부를 조회합니다.
     *
     * @param userId 사용자의 고유 식별자
     * @param time 필터링에 사용될 시간입니다.
     * @param size 반환할 결과의 개수입니다.
     * @return [RoomUserDto] 객체의 리스트입니다.
     */
    fun findRoomsByTime(userId: Long, time: Instant, size: Int): List<RoomUserDto>

    /**
     * 두 사용자 사이의 채팅방을 가져옵니다.
     *
     * @param user1Id 사용자 1의 식별자
     * @param user2Id 사용자 2의 식별자
     * @return [FetchRoomDto] 반환, 조건을 만족하는 값이 없다면 Null 반환
     */
    fun findRoomByUserIds(user1Id: Long, user2Id: Long) : FetchRoomDto?

    /**
     * 특정 사용자의 최근에 채팅이 발생한 방 목록 일부를 조회합니다.
     *
     * @param userId 사용자의 고유 식별자입니다.
     * @param size 반환할 결과의 개수입니다.
     * @return 최근 방을 나타내는 [RoomUserDto] 객체의 리스트입니다.
     */
    fun findRecentRooms(userId: Long, size: Int): List<RoomUserDto>
}
