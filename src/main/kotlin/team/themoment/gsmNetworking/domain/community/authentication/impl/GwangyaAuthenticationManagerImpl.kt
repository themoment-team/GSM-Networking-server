package team.themoment.gsmNetworking.domain.community.authentication.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.domain.community.authentication.GwangyaAuthenticationManager
import team.themoment.gsmNetworking.domain.community.repository.GwangyaTokenRepository

@Component
class GwangyaAuthenticationManagerImpl(
    private val gwangyaTokenRepository: GwangyaTokenRepository
) : GwangyaAuthenticationManager {

    override fun isValidGwangyaToken(gwangyaToken: String): Boolean {
        val token = gwangyaTokenRepository.findByIdOrNull(gwangyaToken)
        println(token)
        return token != null
    }
}
