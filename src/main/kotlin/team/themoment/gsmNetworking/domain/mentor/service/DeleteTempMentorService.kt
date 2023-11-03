package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.mentor.repository.TempMentorRepository

@Service
@Transactional(rollbackFor = [Exception::class])
class DeleteTempMentorService(
    private val tempMentorRepository: TempMentorRepository
) {

    fun execute(firebaseId: String) {
        val tempMentor = tempMentorRepository.findByFirebaseId(firebaseId)
        tempMentor.deleted = true
    }

}