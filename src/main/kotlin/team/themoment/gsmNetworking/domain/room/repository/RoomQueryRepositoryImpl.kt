package team.themoment.gsmNetworking.domain.room.repository

import team.themoment.gsmNetworking.domain.room.domain.QRoom.room
import team.themoment.gsmNetworking.domain.room.domain.QRoomUser.roomUser
import com.querydsl.jpa.impl.JPAQueryFactory

class RoomQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : RoomQueryRepository {
    
}
