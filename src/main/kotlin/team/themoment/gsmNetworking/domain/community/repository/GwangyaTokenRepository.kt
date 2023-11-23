package team.themoment.gsmNetworking.domain.community.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.community.domain.GwangyaToken

interface GwangyaTokenRepository : CrudRepository<GwangyaToken, String> {
}
