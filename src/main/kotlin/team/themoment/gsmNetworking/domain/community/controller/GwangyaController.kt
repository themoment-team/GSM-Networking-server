package team.themoment.gsmNetworking.domain.community.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.domain.community.dto.GwangyaTokenDto
import team.themoment.gsmNetworking.domain.community.repository.GwangyaTokenRepository
import team.themoment.gsmNetworking.domain.community.service.GwangyaGenerationService

@RestController
@RequestMapping("api/v1/gwangya")
class GwangyaController(
    private val gwangyaGenerationService: GwangyaGenerationService,
) {

    @PostMapping("/token")
    fun generateGwangyaToken(): ResponseEntity<GwangyaTokenDto> {
        val gwangyaToken = gwangyaGenerationService.execute()
        return ResponseEntity.ok(gwangyaToken)
    }
}
