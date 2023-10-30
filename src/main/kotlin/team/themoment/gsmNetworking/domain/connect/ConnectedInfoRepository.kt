package team.themoment.gsmNetworking.domain.connect

import org.springframework.data.repository.CrudRepository

interface ConnectedInfoRepository : CrudRepository<ConnectInfo, String> {
    fun findByUserId(userId: Long): List<ConnectInfo>
}
