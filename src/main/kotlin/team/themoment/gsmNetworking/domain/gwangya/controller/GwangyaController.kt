package team.themoment.gsmNetworking.domain.gwangya.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.gwangya.authentication.GwangyaAuthenticationManager
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostDto
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostRegistrationDto
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaTokenDto
import team.themoment.gsmNetworking.domain.gwangya.service.GenerateGwangyaPostService
import team.themoment.gsmNetworking.domain.gwangya.service.QueryGwangyaPostService
import team.themoment.gsmNetworking.domain.gwangya.service.QueryGwangyaTokenService
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/gwangya")
class GwangyaController(
    private val queryGwangyaTokenService: QueryGwangyaTokenService,
    private val generateGwangyaPostService: GenerateGwangyaPostService,
    private val queryGwangyaPostService: QueryGwangyaPostService,
    private val gwangyaAuthenticationManager: GwangyaAuthenticationManager
) {
    @GetMapping("/token")
    fun queryGwangyaToken(): ResponseEntity<GwangyaTokenDto> {
        val gwangyaToken = queryGwangyaTokenService.execute()
        return ResponseEntity.ok(gwangyaToken)
    }

    @GetMapping
    fun queryGwangya(
        @RequestHeader("gwangyaToken") gwangyaToken: String,
        @RequestParam("gwangyaId") cursorId: Long,
        @RequestParam pageSize: Long
    ): ResponseEntity<List<GwangyaPostDto>> {
        checkGwangyaAuthentication(gwangyaToken)
        if (pageSize < 0L || cursorId < 0L)
            throw ExpectedException("0이상부터 가능합니다.", HttpStatus.BAD_REQUEST)
        else if (pageSize > 20L)
            throw ExpectedException("페이지 크기는 20이하까지 가능합니다.", HttpStatus.BAD_REQUEST)
        val gwangyaPosts = queryGwangyaPostService.execute(cursorId, pageSize)
        return ResponseEntity.ok(gwangyaPosts)
    }

    @PostMapping
    fun generateGwangya(
        @RequestHeader("gwangyaToken") gwangyaToken: String,
        @Valid @RequestBody gwangyaDto: GwangyaPostRegistrationDto
    ): ResponseEntity<GwangyaPostDto> {
        checkGwangyaAuthentication(gwangyaToken)
        val gwangyaPost = generateGwangyaPostService.execute(gwangyaDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(gwangyaPost)
    }

    private fun checkGwangyaAuthentication(gwangyaToken: String) {
        if (!gwangyaAuthenticationManager.isValidGwangyaToken(gwangyaToken))
            throw ExpectedException("광야 인증에 실패하였습니다.", HttpStatus.UNAUTHORIZED)
    }
}
