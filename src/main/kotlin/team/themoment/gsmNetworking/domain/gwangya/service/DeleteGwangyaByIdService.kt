package team.themoment.gsmNetworking.domain.gwangya.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.gwangya.repository.GwangyaRepository

@Service
@Transactional(rollbackFor = [Exception::class])
class DeleteGwangyaByIdService(
    private val gwangyaRepository: GwangyaRepository
) {

    fun execute(gwangyaId: Long) {
        val gwangya = gwangyaRepository.findByIdOrNull(gwangyaId)
            ?: throw ExpectedException("광야 게시물을 찾을 수 없습니다. 요청한 gwnagyaId: $gwangyaId", HttpStatus.NOT_FOUND)
        gwangyaRepository.delete(gwangya)
    }
}
