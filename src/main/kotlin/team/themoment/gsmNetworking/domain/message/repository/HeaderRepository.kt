package team.themoment.gsmNetworking.domain.message.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.message.domain.Header


/**
 * [Header] 엔티티를 관리하는 Repository.
 *
 * 복잡한 쿼리 성 메서드의 경우 [MessageRepository]에 구현되어 있습니다.
 */
interface HeaderRepository : CrudRepository<Header, Long> {

}
