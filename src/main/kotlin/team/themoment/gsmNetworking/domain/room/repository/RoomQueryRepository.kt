package team.themoment.gsmNetworking.domain.room.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import team.themoment.gsmNetworking.domain.room.domain.RoomUser
import team.themoment.gsmNetworking.domain.room.dto.domain.RoomUserDto

interface RoomQueryRepository {
    fun findRoomUserOrNull(userId: Long, roomId: Long): RoomUser?
    fun findRoomsRecentUpdated(userId: Long, pageable: Pageable): Page<RoomUserDto>
}
