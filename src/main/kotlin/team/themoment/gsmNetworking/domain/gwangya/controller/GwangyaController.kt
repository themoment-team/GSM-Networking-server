package team.themoment.gsmNetworking.domain.gwangya.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.gwangya.authentication.GwangyaAuthenticationManager
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostsDto
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaTokenDto
import team.themoment.gsmNetworking.domain.gwangya.service.GenerateGwangyaPostsService
import team.themoment.gsmNetworking.domain.gwangya.service.QueryGwangyaTokenService
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/gwangya")
class GwangyaController(
    private val queryGwangyaTokenService: QueryGwangyaTokenService,
    private val generateGwangyaPostsService: GenerateGwangyaPostsService,
    private val gwangyaAuthenticationManager: GwangyaAuthenticationManager
) {
    @GetMapping("/token")
    fun queryGwangyaToken(): ResponseEntity<GwangyaTokenDto> {
        val gwangyaToken = queryGwangyaTokenService.execute()
        return ResponseEntity.ok(gwangyaToken)
    }

    @PostMapping
    fun generateGwangya(
        @RequestHeader("gwangyaToken") gwangyaToken: String,
        @Valid @RequestBody gwangyaDto: GwangyaPostsDto
    ): ResponseEntity<Void> {
        checkGwangyaAuthentication(gwangyaToken)
        generateGwangyaPostsService.execute(gwangyaDto)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    private fun checkGwangyaAuthentication(gwangyaToken: String) {
        if (!gwangyaAuthenticationManager.isValidGwangyaToken(gwangyaToken))
            throw ExpectedException("광야 인증에 실패하였습니다.", HttpStatus.UNAUTHORIZED)
    }
}
