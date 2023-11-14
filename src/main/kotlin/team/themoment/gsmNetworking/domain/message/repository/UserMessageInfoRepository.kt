package team.themoment.gsmNetworking.domain.message.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.message.domain.UserMessageInfo


interface UserMessageInfoRepository : CrudRepository<UserMessageInfo, Long> {
    fun findByUserId(userId: Long): UserMessageInfo?

}
