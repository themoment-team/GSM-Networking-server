package team.themoment.gsmNetworking.domain.room.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.room.domain.ConnectedRoomUser
import team.themoment.gsmNetworking.domain.room.domain.Room

interface ConnectedRoomUserRepository : CrudRepository<ConnectedRoomUser, Room> {
}
