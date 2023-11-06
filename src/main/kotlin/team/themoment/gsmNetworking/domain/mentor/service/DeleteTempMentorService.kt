package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.mentor.repository.TempMentorRepository

@Service
@Transactional(rollbackFor = [Exception::class])
class DeleteTempMentorService(
    private val tempMentorRepository: TempMentorRepository
) {

    fun execute(firebaseId: String) {
        val tempMentor = tempMentorRepository.findByFirebaseId(firebaseId)
            ?: throw ExpectedException("존재하지 않은 firebaseId : $firebaseId 입니다.", HttpStatus.NOT_FOUND)
        tempMentor.deleted = true
    }

}
