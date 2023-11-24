package team.themoment.gsmNetworking.domain.community.authentication

interface GwangyaAuthenticationManager {

    fun isValidGwangyaToken(gwangyaToken: String): Boolean
}
