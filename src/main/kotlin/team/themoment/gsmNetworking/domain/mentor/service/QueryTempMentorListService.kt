package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorListDto
import team.themoment.gsmNetworking.domain.mentor.repository.TempMentorRepository

/**
 * 임시멘토의 정보를 가져오는 로직이 담긴 클래스 입니다.
 */
@Service
@Transactional(readOnly = true)
class QueryTempMentorListService (
    private val tempMentorRepository: TempMentorRepository
) {

    /**
     * 임시멘토의 정보를 가져옵니다.
     *
     * @return 임시멘토 정보가 담긴 dto
     */
    fun execute(): TempMentorListDto {
        val tempMentors = tempMentorRepository.findAll().map { tempMentor ->
            TempMentorInfoDto(
                tempMentor.id,
                tempMentor.name,
                tempMentor.email,
                tempMentor.generation,
                tempMentor.position,
                TempMentorInfoDto.CompanyInfoDto(
                    tempMentor.companyName,
                    tempMentor.companyUrl),
                tempMentor.sns
            )
        }

        return TempMentorListDto(tempMentors)
    }

}
