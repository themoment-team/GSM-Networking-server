package team.themoment.gsmNetworking.domain.gwangya.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.gwangya.domain.Gwangya
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostsDto
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostsRegistrationDto
import team.themoment.gsmNetworking.domain.gwangya.repository.GwangyaRepository

@Service
@Transactional(rollbackFor = [Exception::class])
class GenerateGwangyaPostsService(
    private val gwangyaRepository: GwangyaRepository
) {

    fun execute(gwangyaPostsDto: GwangyaPostsRegistrationDto): GwangyaPostsDto {

        val gwangyaPosts = Gwangya(
            content = gwangyaPostsDto.content
        )

        val savedGwangyaPost = gwangyaRepository.save(gwangyaPosts)

        return GwangyaPostsDto(
            savedGwangyaPost.id,
            savedGwangyaPost.content,
            savedGwangyaPost.createdAt
        )
    }
}
