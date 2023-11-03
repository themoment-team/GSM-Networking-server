package team.themoment.gsmNetworking.domain.room.service

import team.themoment.gsmNetworking.domain.room.dto.domain.FetchRoomDto
import team.themoment.gsmNetworking.domain.room.dto.domain.RoomUserDto
import java.time.Instant


interface QueryRoomService {
    fun recentRooms(userId: Long, size: Int): List<RoomUserDto>

    fun findRoomByUserIds(user1Id: Long, user2Id: Long): FetchRoomDto?

    fun roomsByTime(userId: Long, time: Instant, size: Int): List<RoomUserDto>
}
