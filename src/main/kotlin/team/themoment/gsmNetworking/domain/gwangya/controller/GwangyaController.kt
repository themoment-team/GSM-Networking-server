package team.themoment.gsmNetworking.domain.gwangya.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
import team.themoment.gsmNetworking.domain.gwangya.service.DeleteGwangyaPostByIdUseCase
import team.themoment.gsmNetworking.domain.gwangya.service.GenerateGwangyaPostUseCase
import team.themoment.gsmNetworking.domain.gwangya.service.QueryGwangyaPostUseCase
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/gwangya")
class GwangyaController(
    private val generateGwangyaPostUseCase: GenerateGwangyaPostUseCase,
    private val queryGwangyaPostUseCase: QueryGwangyaPostUseCase,
    private val deleteGwangyaPostByIdUseCase: DeleteGwangyaPostByIdUseCase,
    private val gwangyaAuthenticationManager: GwangyaAuthenticationManager
) {

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
        val gwangyaPosts = queryGwangyaPostUseCase.queryGwangyaPost(cursorId, pageSize)
        return ResponseEntity.ok(gwangyaPosts)
    }

    @PostMapping
    fun generateGwangya(
        @RequestHeader("gwangyaToken") gwangyaToken: String,
        @Valid @RequestBody gwangyaDto: GwangyaPostRegistrationDto
    ): ResponseEntity<GwangyaPostDto> {
        checkGwangyaAuthentication(gwangyaToken)
        val savedGwangyaPost = generateGwangyaPostUseCase.generateGwangyaPost(gwangyaDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGwangyaPost)
    }

    @DeleteMapping("/{gwangyaId}")
    fun deleteGwangya(
        @PathVariable gwangyaId: Long
    ): ResponseEntity<Void> {
        deleteGwangyaPostByIdUseCase.deleteGwangyaPostById(gwangyaId)
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).build()
    }

    private fun checkGwangyaAuthentication(gwangyaToken: String) {
        if (!gwangyaAuthenticationManager.isValidGwangyaToken(gwangyaToken))
            throw ExpectedException("광야 인증에 실패하였습니다.", HttpStatus.UNAUTHORIZED)
    }
}
