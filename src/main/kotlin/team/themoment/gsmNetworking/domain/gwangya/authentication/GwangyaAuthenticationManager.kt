package team.themoment.gsmNetworking.domain.gwangya.authentication

interface GwangyaAuthenticationManager {

    fun isValidGwangyaToken(gwangyaToken: String): Boolean
}
