package team.themoment.gsmNetworking.domain.gwangya.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaTokenDto
import team.themoment.gsmNetworking.domain.gwangya.service.QueryGwangyaTokenUseCase

@RestController
@RequestMapping("api/v1/gwangya/token")
class GwangyaTokenController(
    private val queryGwangyaTokenUseCase: QueryGwangyaTokenUseCase
) {

    @GetMapping
    fun queryGwangyaToken(): ResponseEntity<GwangyaTokenDto> {
        val gwangyaToken = queryGwangyaTokenUseCase.queryGwangyaToken()
        return ResponseEntity.ok(gwangyaToken)
    }
}
