package team.themoment.gsmNetworking.global.security.principal

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.auth.repository.AuthenticationRepository

@Service
class AuthenticationDetailsService(
    private val authenticationRepository: AuthenticationRepository
) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        val authentication = authenticationRepository.findByIdOrNull(username.toLong())
            ?: throw ExpectedException("인증된 사용자가 가지는 id를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
        return AuthenticationDetails(authentication)
    }

}
