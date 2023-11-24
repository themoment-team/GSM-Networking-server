package team.themoment.gsmNetworking.domain.gwangya.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.gwangya.domain.Gwangya
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostsDto
import team.themoment.gsmNetworking.domain.gwangya.repository.GwangyaRepository
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class GenerateGwangyaPostsService(
    private val gwangyaRepository: GwangyaRepository
) {

    fun execute(gwangyaPostsDto: GwangyaPostsDto) {

        val gwangyaPosts = Gwangya(
            content = gwangyaPostsDto.content,
            generationTime = LocalDateTime.now()
        )

        gwangyaRepository.save(gwangyaPosts)
    }
}
