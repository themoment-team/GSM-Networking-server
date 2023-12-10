package team.themoment.gsmNetworking.domain.message.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.message.domain.Header


interface HeaderRepository : CrudRepository<Header, Long> {

}
