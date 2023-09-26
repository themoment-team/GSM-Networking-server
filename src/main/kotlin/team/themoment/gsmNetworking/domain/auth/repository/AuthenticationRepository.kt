package team.themoment.gsmNetworking.domain.auth.repository

import team.themoment.gsmNetworking.domain.auth.domain.Authentication
import org.springframework.data.repository.CrudRepository

/**
 * Authentication Entity를 위한 Repository 인터페이스 입니다.
 */
interface AuthenticationRepository: CrudRepository<Authentication, String> {

    fun findByEmail(email: String): Authentication?
    fun findByProviderId(providerId: String): Authentication?

}