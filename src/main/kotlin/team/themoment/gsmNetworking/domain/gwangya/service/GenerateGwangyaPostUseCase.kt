package team.themoment.gsmNetworking.domain.gwangya.service

import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostDto
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostRegistrationDto

interface GenerateGwangyaPostUseCase {

    fun generateGwangyaPost(gwangyaPostDto: GwangyaPostRegistrationDto): GwangyaPostDto
}
