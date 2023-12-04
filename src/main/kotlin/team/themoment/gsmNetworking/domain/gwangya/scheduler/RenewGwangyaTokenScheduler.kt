package team.themoment.gsmNetworking.domain.gwangya.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.domain.gwangya.service.GenerateGwangyaTokenService

@Component
class RenewGwangyaTokenScheduler(
    private val generateGwangyaTokenService: GenerateGwangyaTokenService
) {

    @Scheduled(fixedRateString = "\${gwangya.token.token-renewal-time}")
    fun renewGwangyaToken(){
        generateGwangyaTokenService.execute()
    }
}