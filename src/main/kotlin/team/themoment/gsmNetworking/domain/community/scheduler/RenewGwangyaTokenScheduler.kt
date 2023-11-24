package team.themoment.gsmNetworking.domain.community.scheduler

import net.bytebuddy.utility.RandomString
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.domain.community.domain.GwangyaToken
import team.themoment.gsmNetworking.domain.community.properties.GwangyaProperties
import team.themoment.gsmNetworking.domain.community.repository.GwangyaTokenRepository
import java.time.LocalDateTime

@Component
class RenewGwangyaTokenScheduler(
    private val gwangyaProperties: GwangyaProperties,
    private val gwangyaTokenRepository: GwangyaTokenRepository,
) {

    @Scheduled(fixedRateString = "\${gwangya.token.token-renewal-time}")
    fun generateGwangyaToken() {
        val gwangyaToken = RandomString.make(20);
        val nowTime = LocalDateTime.now()

        saveGwangyaToken("token", gwangyaToken, nowTime, gwangyaProperties.tokenExp)
    }

    private fun saveGwangyaToken(tokenId: String, gwangyaToken: String, nowTime: LocalDateTime, expirationTime: Long) {
        gwangyaTokenRepository.save(
            GwangyaToken(
                tokenId,
                gwangyaToken,
                nowTime,
                expirationTime
            )
        )
    }
}
