package team.themoment.gsmNetworking.domain.message.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.message.domain.Message
import java.util.*


/**
 * [Message] 엔티티를 관리하는 Repository.
 */
interface MessageRepository : CrudRepository<Message, UUID>, MessageCustomRepository
