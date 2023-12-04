package team.themoment.gsmNetworking.domain.message.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.message.domain.Message
import java.util.*


interface MessageRepository : CrudRepository<Message, UUID>, MessageCustomRepository {

}
