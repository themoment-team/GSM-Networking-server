package team.themoment.gsmNetworking.domain.gwangya.service

import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostDto

interface QueryGwangyaPostUseCase {

    fun queryGwangyaPost(cursorId: Long, pageSize: Long): List<GwangyaPostDto>
}