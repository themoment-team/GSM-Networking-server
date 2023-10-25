package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.stereotype.Service
import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorListDto
import team.themoment.gsmNetworking.domain.mentor.repository.TempMentorRepository

@Service
class QueryTempMentorListService (
    private val tempMentorRepository: TempMentorRepository
) {
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
                tempMentor.sns)
        };
        return TempMentorListDto(tempMentors)
    }
}
