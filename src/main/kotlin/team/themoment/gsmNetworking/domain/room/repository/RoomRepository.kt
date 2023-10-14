package team.themoment.gsmNetworking.domain.room.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.room.domain.Room

interface RoomRepository : CrudRepository<Room, Long>, RoomQueryRepository {
}
