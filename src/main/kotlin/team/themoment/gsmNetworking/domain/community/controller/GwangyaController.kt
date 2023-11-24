package team.themoment.gsmNetworking.domain.community.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.community.authentication.GwangyaAuthenticationManager
import team.themoment.gsmNetworking.domain.community.dto.GwangyaPostsDto
import team.themoment.gsmNetworking.domain.community.dto.GwangyaTokenDto
import team.themoment.gsmNetworking.domain.community.service.GenerateGwangyaTokenService
import team.themoment.gsmNetworking.domain.community.service.GenerateGwangyaPostsService
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/gwangya")
class GwangyaController(
    private val generateGwangyaTokenService: GenerateGwangyaTokenService,
    private val generateGwangyaPostsService: GenerateGwangyaPostsService,
    private val gwangyaAuthenticationManager: GwangyaAuthenticationManager
) {

    @PostMapping("/token")
    fun generateGwangyaToken(): ResponseEntity<GwangyaTokenDto> {
        val gwangyaToken = generateGwangyaTokenService.execute()
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
