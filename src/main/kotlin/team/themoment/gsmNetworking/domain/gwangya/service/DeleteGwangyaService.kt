package team.themoment.gsmNetworking.domain.gwangya.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.gwangya.repository.GwangyaRepository

@Service
@Transactional(rollbackFor = [Exception::class])
class DeleteGwangyaService(
    private val gwangyaRepository: GwangyaRepository
) {

    fun execute(gwangyaId: Long) {
        gwangyaRepository.deleteById(gwangyaId)
    }
}
