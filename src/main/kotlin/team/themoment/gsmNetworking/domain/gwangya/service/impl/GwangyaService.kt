package team.themoment.gsmNetworking.domain.gwangya.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.gwangya.domain.Gwangya
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostDto
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostRegistrationDto
import team.themoment.gsmNetworking.domain.gwangya.repository.GwangyaRepository
import team.themoment.gsmNetworking.domain.gwangya.service.DeleteGwangyaPostByIdUseCase
import team.themoment.gsmNetworking.domain.gwangya.service.GenerateGwangyaPostUseCase
import team.themoment.gsmNetworking.domain.gwangya.service.QueryGwangyaPostUseCase

@Service
class GwangyaService(
    private val gwangyaRepository: GwangyaRepository
) : QueryGwangyaPostUseCase,
    GenerateGwangyaPostUseCase,
    DeleteGwangyaPostByIdUseCase {

    @Transactional(readOnly = true)
    override fun queryGwangyaPost(cursorId: Long, pageSize: Long): List<GwangyaPostDto> =
        if (cursorId == 0L)
            gwangyaRepository.findPageWithRecentPosts(pageSize)
        else
            gwangyaRepository.findPagebyCursorId(cursorId, pageSize)

    @Transactional(rollbackFor = [Exception::class])
    override fun generateGwangyaPost(gwangyaPostDto: GwangyaPostRegistrationDto): GwangyaPostDto {
        val gwangyaPost = Gwangya(
            content = gwangyaPostDto.content,
        )

        val savedGwangyaPost = gwangyaRepository.save(gwangyaPost)

        return GwangyaPostDto(
            id = savedGwangyaPost.id,
            content = savedGwangyaPost.content,
            createdAt = savedGwangyaPost.createdAt
        )
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteGwangyaPostById(gwangyaId: Long) {
        val gwangya = gwangyaRepository.findByIdOrNull(gwangyaId)
            ?: throw ExpectedException("광야 게시물을 찾을 수 없습니다. 요청한 gwnagyaId: $gwangyaId", HttpStatus.NOT_FOUND)
        gwangyaRepository.delete(gwangya)
    }
}
