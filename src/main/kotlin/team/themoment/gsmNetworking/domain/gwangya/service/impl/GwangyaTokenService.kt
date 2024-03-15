package team.themoment.gsmNetworking.domain.gwangya.service.impl

import net.bytebuddy.utility.RandomString
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.gwangya.domain.GwangyaToken
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaTokenDto
import team.themoment.gsmNetworking.domain.gwangya.properties.GwangyaProperties
import team.themoment.gsmNetworking.domain.gwangya.repository.GwangyaTokenRepository
import team.themoment.gsmNetworking.domain.gwangya.service.GenerateGwangyaTokenUseCase
import team.themoment.gsmNetworking.domain.gwangya.service.QueryGwangyaTokenUseCase
import java.time.LocalDateTime

@Service
class GwangyaTokenService(
    private val gwangyaTokenRepository: GwangyaTokenRepository,
    private val gwangyaProperties: GwangyaProperties,
) : GenerateGwangyaTokenUseCase,
    QueryGwangyaTokenUseCase {

    @Transactional(readOnly = true)
    override fun queryGwangyaToken(): GwangyaTokenDto {
        val token = gwangyaTokenRepository.findById("token").get()
        val expiredTime = LocalDateTime.now().plusSeconds(token.expirationTime)

        return GwangyaTokenDto(
            gwangyaToken = token.gwangyaToken,
            expiredTime = expiredTime
        )
    }

    @Transactional
    override fun generateGwangyaToken() {
        val gwangyaToken = RandomString.make(20);
        val nowTime = LocalDateTime.now()

        saveGwangyaToken("token", gwangyaToken, nowTime, gwangyaProperties.tokenExp)
    }

    private fun saveGwangyaToken(tokenId: String, gwangyaToken: String, nowTime: LocalDateTime, expirationTime: Long) =
        gwangyaTokenRepository.save(
            GwangyaToken(
                id = tokenId,
                gwangyaToken = gwangyaToken,
                createdAt = nowTime,
                expirationTime = expirationTime
            )
        )
}
