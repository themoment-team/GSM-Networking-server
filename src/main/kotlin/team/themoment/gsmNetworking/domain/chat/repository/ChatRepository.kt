package team.themoment.gsmNetworking.domain.chat.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.chat.domain.BaseChat

interface ChatRepository : CrudRepository<BaseChat, Long>, ChatQueryRepository {
}
