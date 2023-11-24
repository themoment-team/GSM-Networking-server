package team.themoment.gsmNetworking.domain.gwangya.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaTokenDto
import team.themoment.gsmNetworking.domain.gwangya.repository.GwangyaTokenRepository
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class QueryGwangyaTokenService(
    private val gwangyaTokenRepository: GwangyaTokenRepository
) {

    fun execute(): GwangyaTokenDto {
        val token = gwangyaTokenRepository.findById("token").get()
        val expiredTime = LocalDateTime.now().plusSeconds(token.expirationTime)

        return GwangyaTokenDto(
            token.gwangyaToken,
            expiredTime
        )
    }
}
