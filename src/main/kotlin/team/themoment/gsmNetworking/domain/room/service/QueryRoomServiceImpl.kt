package team.themoment.gsmNetworking.domain.room.service

import org.springframework.stereotype.Service
import team.themoment.gsmNetworking.domain.room.dto.domain.RoomUserDto
import team.themoment.gsmNetworking.domain.room.repository.RoomRepository
import team.themoment.gsmNetworking.domain.user.repository.UserRepository
import java.lang.IllegalArgumentException
import java.time.Instant

@Service
class QueryRoomServiceImpl(
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository
) : QueryRoomService {
    override fun recentRooms(userId: Long, size: Int): List<RoomUserDto> {
        validUser(userId)
        return roomRepository.findRecentRooms(userId, size)
    }

    override fun roomsByTime(userId: Long, time: Instant, size: Int): List<RoomUserDto> {
        validUser(userId)
        return roomRepository.findRoomsByTime(userId, time, size)
    }

    private fun validUser(userId: Long) {
        if(!userRepository.existsById(userId)) {
            throw IllegalArgumentException("유효하지 않은 userId 입니다. userId가 $userId 인 User를 찾을 수 없습니다.")
        }
    }
}
