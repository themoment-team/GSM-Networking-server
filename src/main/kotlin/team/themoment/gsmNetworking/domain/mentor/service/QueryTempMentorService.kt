package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.mentor.dto.CompanyInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.repository.TempMentorRepository

/**
 * 특정 임시멘토의 정보를 가져오는 로직이 담긴 클래스입니다.
 */
@Service
@Transactional(readOnly = true)
class QueryTempMentorService(
    private val tempMentorRepository: TempMentorRepository
) {

    /**
     * 특정 임시멘토의 정보를 가져오는 메서드입니다,.
     *
     * @param firebaseId 임시멘토의 식별자
     * @return 특정 임시멘토의 정보를 담은 dto
     */
    fun execute(firebaseId: String): TempMentorInfoDto {
        val tempMentor = tempMentorRepository.findByFirebaseId(firebaseId)
            ?: throw ExpectedException("존재하지 않는 firebaseId : $firebaseId 입니다.", HttpStatus.NOT_FOUND)

        return TempMentorInfoDto(
            tempMentor.id,
            tempMentor.firebaseId,
            tempMentor.name,
            tempMentor.email ?: "",
            tempMentor.generation,
            tempMentor.position,
            CompanyInfoDto(
                tempMentor.companyName,
                tempMentor.companyUrl
            ),
            tempMentor.sns
        )
    }
}
