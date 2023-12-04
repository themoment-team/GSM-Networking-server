package team.themoment.gsmNetworking.domain.gwangya.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.gwangya.domain.GwangyaToken

interface GwangyaTokenRepository : CrudRepository<GwangyaToken, String> {
    fun findByGwangyaToken(gwangyaToken: String): GwangyaToken?
}
