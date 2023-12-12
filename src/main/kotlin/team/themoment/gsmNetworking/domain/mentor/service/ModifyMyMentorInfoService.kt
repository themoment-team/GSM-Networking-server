package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.mentor.domain.Career
import team.themoment.gsmNetworking.domain.mentor.dto.MentorSaveInfoDto
import team.themoment.gsmNetworking.domain.mentor.repository.CareerRepository
import team.themoment.gsmNetworking.domain.mentor.repository.MentorRepository
import team.themoment.gsmNetworking.domain.user.dto.UserSaveInfoDto
import team.themoment.gsmNetworking.domain.user.service.ModifyMyUserInfoService

@Service
@Transactional(rollbackFor = [Exception::class])
class ModifyMyMentorInfoService(
    private val careerRepository: CareerRepository,
    private val mentorRepository: MentorRepository,
    private val modifyMyUserInfoService: ModifyMyUserInfoService
) {

    fun execute(mentorSaveInfoDto: MentorSaveInfoDto) {
        val mentor = mentorRepository.findByIdOrNull(mentorSaveInfoDto.id)
            ?: throw ExpectedException("mentor를 찾을 수 없습니다", HttpStatus.NOT_FOUND)

        val updatedCareerList = mentorSaveInfoDto.career.map {
            Career(
                it.id,
                mentor,
                it.companyName,
                it.companyUrl ?: "",
                it.position,
                it.startDate,
                it.endDate,
                it.isWorking ?: false
            )
        }

        val userSaveInfoDto = UserSaveInfoDto(
            mentorSaveInfoDto.name,
            mentorSaveInfoDto.generation,
            mentorSaveInfoDto.phoneNumber,
            mentorSaveInfoDto.email,
            mentorSaveInfoDto.snsUrl,
            mentorSaveInfoDto.profileUrl
        )

        modifyMyUserInfoService.execute(mentor.user.authenticationId, userSaveInfoDto)
        careerRepository.saveAll(updatedCareerList)
    }
}
