package team.themoment.gsmNetworking.domain.community.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.community.domain.Gwangya
import team.themoment.gsmNetworking.domain.community.dto.GwangyaPostsDto
import team.themoment.gsmNetworking.domain.community.repository.GwangyaRepository

@Service
@Transactional(rollbackFor = [Exception::class])
class GenerateGwangyaPostsService(
    private val gwangyaRepository: GwangyaRepository
) {

    fun execute(gwangyaPostsDto: GwangyaPostsDto) {
        val gwangyaPosts = Gwangya(
            content = gwangyaPostsDto.content
        )

        gwangyaRepository.save(gwangyaPosts)
    }
}
