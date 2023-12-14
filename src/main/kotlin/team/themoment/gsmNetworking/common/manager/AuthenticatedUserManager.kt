package team.themoment.gsmNetworking.common.manager

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.auth.domain.Authentication
import team.themoment.gsmNetworking.domain.auth.domain.Authority
import team.themoment.gsmNetworking.domain.auth.repository.AuthenticationRepository

@Component
class AuthenticatedUserManager(
    private val authenticationRepository: AuthenticationRepository
) {

    /**
     * SecurityContextHolder에 담긴 authentication의 name을 가져옵니다.
     *
     * @return authentication 객체의 name을 Long으로 변환한 값
     */
    fun getName(): Long {
        val securityContextHolder = SecurityContextHolder.getContext()
        val authentication = securityContextHolder.authentication
        return authentication.name.toLong()
    }

    /**
     * authentication 엔티티의 권한을 update 합니다.
     *
     * @param newAuthority 수정할 사용자의 새로운 권한
     * @throws ExpectedException 이 터지는 조건은 아래와 같다.
     *      1. authenticationId를 가진 사용자가 없는 경우
     */
    @Transactional(rollbackFor = [Exception::class])
    fun updateAuthority(newAuthority: Authority) {
        val authenticationId = this.getName()
        val authentication = authenticationRepository.findByIdOrNull(authenticationId)
            ?: throw ExpectedException("인증 절차를 수행하지 않아 id가 없는 사용자 입니다.", HttpStatus.NOT_FOUND)
        authenticationRepository.save(
            Authentication(
                id = authenticationId,
                email = authentication.email,
                providerId = authentication.providerId,
                authority = newAuthority
            )
        )
    }

}
