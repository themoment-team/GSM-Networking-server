package team.themoment.gsmNetworking.domain.mentor.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.mentor.dto.CompanyInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.SearchTempMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.repository.TempMentorRepository
import team.themoment.gsmNetworking.domain.mentor.service.DeleteTempMentorByIdUseCase
import team.themoment.gsmNetworking.domain.mentor.service.QueryAllTempMentorsUseCase
import team.themoment.gsmNetworking.domain.mentor.service.QueryTempMentorByIdUseCase
import team.themoment.gsmNetworking.domain.mentor.service.QueryTempMentorsByNameUseCase

@Service
class TempMentorService(
    private val tempMentorRepository: TempMentorRepository,
) : QueryAllTempMentorsUseCase,
    QueryTempMentorByIdUseCase,
    QueryTempMentorsByNameUseCase,
    DeleteTempMentorByIdUseCase {

    /**
     * 임시멘토의 정보를 가져옵니다.
     *
     * @return 임시멘토 정보가 담긴 dto
     */
    @Transactional(readOnly = true)
    override fun queryAllTempMentors(): List<TempMentorInfoDto> {
        val tempMentors = tempMentorRepository.findAll().map { tempMentor ->
            TempMentorInfoDto(
                id = tempMentor.id,
                name = tempMentor.name,
                email = tempMentor.email ?: "",
                generation = tempMentor.generation,
                position = tempMentor.position,
                company = CompanyInfoDto(
                    name = tempMentor.companyName,
                    url = tempMentor.companyUrl
                ),
                snsUrl = tempMentor.sns,
                temporaryImgNumber = tempMentor.temporaryImgNumber
            )
        }

        return tempMentors
    }

    /**
     * 특정 임시멘토의 정보를 가져오는 메서드입니다,.
     *
     * @param id 임시멘토의 식별자
     * @return 특정 임시멘토의 정보를 담은 dto
     */
    @Transactional(readOnly = true)
    override fun queryTempMentorById(id: Long): TempMentorInfoDto {
        val tempMentor = tempMentorRepository.findByIdOrNull(id)
            ?: throw ExpectedException("존재하지 않는 id 입니다.", HttpStatus.NOT_FOUND)

        return TempMentorInfoDto(
            id = tempMentor.id,
            name = tempMentor.name,
            email = tempMentor.email ?: "",
            generation = tempMentor.generation,
            position = tempMentor.position,
            company = CompanyInfoDto(
                name = tempMentor.companyName,
                url = tempMentor.companyUrl
            ),
            snsUrl = tempMentor.sns,
            temporaryImgNumber = tempMentor.temporaryImgNumber
        )
    }

    /**
     * 임시멘토의 정보 리스트를 가져옵니다.
     *
     * @param userName 가져올 유저의 이름
     * @return 가져온 임시멘토 정보가 담긴 dto
     */
    @Transactional(readOnly = true)
    override fun queryTempMentorsByName(name: String): List<SearchTempMentorInfoDto> {
        val searchTempMentors = tempMentorRepository.findByName(name).map { tempMentor ->
            SearchTempMentorInfoDto(
                id = tempMentor.id,
                name = tempMentor.name,
                email = tempMentor.email,
                generation = tempMentor.generation,
                position = tempMentor.position,
                company = CompanyInfoDto(
                    name = tempMentor.companyName,
                    url = tempMentor.companyUrl
                ),
                snsUrl = tempMentor.sns
            )
        }

        return searchTempMentors
    }

    /**
     * 특정 임시멘토를 삭제합니다.
     *
     * @param id 임시멘토의 식별자
     */
    @Transactional
    override fun deleteTempMentorById(id: Long) {
        val tempMentor = tempMentorRepository.findByIdOrNull(id)
            ?: throw ExpectedException("존재하지 않는 id 입니다.", HttpStatus.NOT_FOUND)
        tempMentor.deleted = true
    }
}
