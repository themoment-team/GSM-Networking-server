package team.themoment.gsmNetworking.domain.gwangya.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostsDto
import team.themoment.gsmNetworking.domain.gwangya.repository.GwangyaCustomRepository

@Service
@Transactional(readOnly = true)
class QueryGwangyaPostsService(
    private val gwangyaRepository: GwangyaCustomRepository
) {

    fun execute(cursorId: Long, pageSize: Int): List<GwangyaPostsDto> =
        gwangyaRepository.findAll(cursorId, pageSize)
}
