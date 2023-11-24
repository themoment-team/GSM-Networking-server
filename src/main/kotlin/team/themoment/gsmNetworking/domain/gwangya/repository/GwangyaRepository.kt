package team.themoment.gsmNetworking.domain.gwangya.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.gwangya.domain.Gwangya
import java.util.UUID

interface GwangyaRepository : CrudRepository<Gwangya, UUID> {
}
