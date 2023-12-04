package team.themoment.gsmNetworking.domain.gwangya.service

import net.bytebuddy.utility.RandomString
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.gwangya.domain.GwangyaToken
import team.themoment.gsmNetworking.domain.gwangya.properties.GwangyaProperties
import team.themoment.gsmNetworking.domain.gwangya.repository.GwangyaTokenRepository
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class GenerateGwangyaTokenService(
    private val gwangyaProperties: GwangyaProperties,
    private val gwangyaTokenRepository: GwangyaTokenRepository,
) {

    fun execute() {
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
