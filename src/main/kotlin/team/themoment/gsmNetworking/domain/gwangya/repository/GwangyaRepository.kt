package team.themoment.gsmNetworking.domain.gwangya.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.gwangya.domain.Gwangya

interface GwangyaRepository : CrudRepository<Gwangya, Long>, GwangyaCustomRepository {
}
