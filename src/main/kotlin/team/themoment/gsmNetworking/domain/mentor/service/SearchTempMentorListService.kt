package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.mentor.dto.CompanyInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.SearchTempMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.repository.TempMentorRepository

/**
 * 임시멘토의 정보 리스트를 가져오는 로직이 담긴 클래스 입니다.
 */
@Service
@Transactional(readOnly = true)
class QueryTempMentorListByNameService(
    private val tempMentorRepository: TempMentorRepository
) {

    /**
     * 임시멘토의 정보 리스트를 가져옵니다.
     *
     * @param userName 가져올 유저의 이름
     * @return 가져온 임시멘토 정보가 담긴 dto
     */
    fun execute(name: String): List<SearchTempMentorInfoDto> {
        val searchTempMentors = tempMentorRepository.findByName(name).map { tempMentor ->
            SearchTempMentorInfoDto(
                tempMentor.id,
                tempMentor.name,
                tempMentor.email,
                tempMentor.generation,
                tempMentor.position,
                CompanyInfoDto(
                    tempMentor.companyName,
                    tempMentor.companyUrl
                ),
                tempMentor.sns
            )
        }

        return searchTempMentors
    }
}
