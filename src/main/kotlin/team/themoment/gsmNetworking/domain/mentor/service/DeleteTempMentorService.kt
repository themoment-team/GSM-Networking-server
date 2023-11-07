package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.mentor.repository.TempMentorRepository

/**
 * 임시멘토를 삭제하는 로직이 담긴 클래스입니다.
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class DeleteTempMentorService(
    private val tempMentorRepository: TempMentorRepository
) {

    /**
     * 특정 임시멘토를 삭제합니다.
     *
     * @param firebaseId 임시멘토의 식별자
     */
    fun execute(firebaseId: String) {
        val tempMentor = tempMentorRepository.findByFirebaseId(firebaseId)
            ?: throw ExpectedException("존재하지 않는 firebaseId : $firebaseId 입니다.", HttpStatus.NOT_FOUND)
        tempMentor.deleted = true
    }

}
