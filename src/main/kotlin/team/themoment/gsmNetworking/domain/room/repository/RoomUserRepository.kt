package team.themoment.gsmNetworking.domain.room.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.room.domain.RoomUser

interface RoomUserRepository : CrudRepository<RoomUser, Long> {
}
