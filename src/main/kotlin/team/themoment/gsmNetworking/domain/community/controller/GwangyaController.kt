package team.themoment.gsmNetworking.domain.community.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.domain.community.dto.GwangyaTokenDto
import team.themoment.gsmNetworking.domain.community.service.QueryGwangyaTokenService

@RestController
@RequestMapping("api/v1/gwangya")
class GwangyaController(
    private val queryGwangyaTokenService: QueryGwangyaTokenService,
) {
    @GetMapping("/token")
    fun queryGwangyaToken(): ResponseEntity<GwangyaTokenDto> {
        val gwangyaToken = queryGwangyaTokenService.execute()
        return ResponseEntity.ok(gwangyaToken)
    }
}
