package team.themoment.gsmNetworking.domain.message.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.message.domain.Message


interface MessageRepository : CrudRepository<Message, Long>, MessageCustomRepository {

}
