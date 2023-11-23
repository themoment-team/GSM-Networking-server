package team.themoment.gsmNetworking.domain.community.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.community.domain.Gwangya
import java.util.UUID

interface GwangyaRepository : CrudRepository<Gwangya, UUID> {
}
