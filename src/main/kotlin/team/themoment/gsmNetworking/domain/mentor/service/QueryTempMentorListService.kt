package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.mentor.dto.CompanyInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.repository.TempMentorRepository

/**
 * 임시멘토의 정보를 가져오는 로직이 담긴 클래스 입니다.
 */
@Service
@Transactional(readOnly = true)
class QueryTempMentorListService(
    private val tempMentorRepository: TempMentorRepository
) {

    /**
     * 임시멘토의 정보를 가져옵니다.
     *
     * @return 임시멘토 정보가 담긴 dto
     */
    fun execute(): List<TempMentorInfoDto> {
        val tempMentors = tempMentorRepository.findAllByDeletedIsFalse().map { tempMentor ->
            TempMentorInfoDto(
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

        return tempMentors
    }

}
