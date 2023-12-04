package team.themoment.gsmNetworking.domain.gwangya.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.gwangya.domain.Gwangya
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostsDto
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostsRegistrationDto
import team.themoment.gsmNetworking.domain.gwangya.repository.GwangyaRepository
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class GenerateGwangyaPostsService(
    private val gwangyaRepository: GwangyaRepository
) {

    fun execute(gwangyaPostsDto: GwangyaPostsRegistrationDto): GwangyaPostsDto {

        val gwangyaPosts = Gwangya(
            content = gwangyaPostsDto.content,
            createdAt = LocalDateTime.now()
        )

        val savedGwangyaPost = gwangyaRepository.save(gwangyaPosts)

        return GwangyaPostsDto(
            savedGwangyaPost.gwangyaId,
            savedGwangyaPost.content,
            savedGwangyaPost.createdAt
        )
    }
}
