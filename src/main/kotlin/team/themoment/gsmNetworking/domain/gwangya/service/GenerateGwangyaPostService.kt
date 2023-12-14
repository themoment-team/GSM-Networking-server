package team.themoment.gsmNetworking.domain.gwangya.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.gwangya.domain.Gwangya
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostDto
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostRegistrationDto
import team.themoment.gsmNetworking.domain.gwangya.repository.GwangyaRepository

@Service
@Transactional(rollbackFor = [Exception::class])
class GenerateGwangyaPostService(
    private val gwangyaRepository: GwangyaRepository
) {

    fun execute(gwangyaPostDto: GwangyaPostRegistrationDto): GwangyaPostDto {

        val gwangyaPost = Gwangya(
            content = gwangyaPostDto.content,
        )

        val savedGwangyaPost = gwangyaRepository.save(gwangyaPost)

        return GwangyaPostDto(
            savedGwangyaPost.id,
            savedGwangyaPost.content,
            savedGwangyaPost.createdAt
        )
    }
}
