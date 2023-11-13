package team.themoment.gsmNetworking.global.socket.connect

import org.springframework.data.repository.CrudRepository

interface ConnectedInfoRepository : CrudRepository<ConnectInfo, String> {
    fun findByUserId(userId: Long): List<ConnectInfo>
}
