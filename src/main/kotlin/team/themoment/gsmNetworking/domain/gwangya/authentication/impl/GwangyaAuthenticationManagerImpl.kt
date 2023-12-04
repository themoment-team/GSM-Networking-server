package team.themoment.gsmNetworking.domain.gwangya.authentication.impl

import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.domain.gwangya.authentication.GwangyaAuthenticationManager
import team.themoment.gsmNetworking.domain.gwangya.repository.GwangyaTokenRepository

@Component
class GwangyaAuthenticationManagerImpl(
    private val gwangyaTokenRepository: GwangyaTokenRepository
) : GwangyaAuthenticationManager {

    override fun isValidGwangyaToken(gwangyaToken: String): Boolean =
        gwangyaTokenRepository.findByGwangyaToken(gwangyaToken) != null
}
