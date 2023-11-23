package team.themoment.gsmNetworking.domain.community.service

import net.bytebuddy.utility.RandomString
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.community.domain.GwangyaToken
import team.themoment.gsmNetworking.domain.community.dto.GwangyaTokenDto
import team.themoment.gsmNetworking.domain.community.properties.GwangyaProperties
import team.themoment.gsmNetworking.domain.community.repository.GwangyaTokenRepository
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class GenerateGwangyaTokenService(
    private val gwangyaProperties: GwangyaProperties,
    private val gwangyaTokenRepository: GwangyaTokenRepository
) {

    fun execute(): GwangyaTokenDto {
        val gwangyaToken = RandomString.make(20);
        val nowTime = LocalDateTime.now()
        val expiredTime = nowTime.plusSeconds(gwangyaProperties.tokenExp)
        saveGwangyaToken(gwangyaToken, nowTime, gwangyaProperties.tokenExp)

        return GwangyaTokenDto(
            gwangyaToken,
            expiredTime
        )
    }

    private fun saveGwangyaToken(gwangyaToken: String, nowTime: LocalDateTime, expirationTime: Long) {
        gwangyaTokenRepository.save(
            GwangyaToken(
                gwangyaToken,
                nowTime,
                expirationTime
            )
        )
    }
}
