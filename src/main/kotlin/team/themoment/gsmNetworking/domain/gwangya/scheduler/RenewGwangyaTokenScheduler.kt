package team.themoment.gsmNetworking.domain.gwangya.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.domain.gwangya.service.GenerateGwangyaTokenUseCase

@Component
class RenewGwangyaTokenScheduler(
    private val generateGwangyaTokenUseCase: GenerateGwangyaTokenUseCase
) {

    @Scheduled(fixedRateString = "\${gwangya.token.token-renewal-time}")
    fun renewGwangyaToken(){
        generateGwangyaTokenUseCase.generateGwangyaToken()
    }
}
