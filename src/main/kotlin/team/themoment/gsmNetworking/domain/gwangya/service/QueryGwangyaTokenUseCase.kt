package team.themoment.gsmNetworking.domain.gwangya.service

import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaTokenDto

interface QueryGwangyaTokenUseCase {

    fun queryGwangyaToken(): GwangyaTokenDto
}