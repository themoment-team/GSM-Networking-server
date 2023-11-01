package team.themoment.gsmNetworking.domain.room.service

import team.themoment.gsmNetworking.domain.room.dto.domain.RoomUserDto
import java.time.Instant


interface QueryRoomService {
    fun recentRooms(userId: Long, size: Int) : List<RoomUserDto>

    fun roomsByTime(userId: Long, time: Instant, size: Int) : List<RoomUserDto>
}
