package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.mentor.domain.Career
import team.themoment.gsmNetworking.domain.mentor.dto.MentorCareerDto
import team.themoment.gsmNetworking.domain.mentor.repository.CareerRepository

@Service
@Transactional(rollbackFor = [Exception::class])
class ModifyMyCareerInfoService(
    private val careerRepository: CareerRepository,
) {

    fun execute(mentorCareerDto: MentorCareerDto) {
        val career = careerRepository.findByIdOrNull(mentorCareerDto.id)
            ?: throw ExpectedException("career를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        val updatedCareer = Career(
            career.careerId,
            career.mentor,
            mentorCareerDto.companyName,
            mentorCareerDto.companyUrl ?: "",
            mentorCareerDto.position,
            mentorCareerDto.startDate,
            mentorCareerDto.endDate,
            mentorCareerDto.isWorking ?: false
        )

        careerRepository.save(updatedCareer)
    }
}
