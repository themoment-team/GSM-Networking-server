package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.domain.Career
import team.themoment.gsmNetworking.domain.mentor.domain.Mentor
import team.themoment.gsmNetworking.domain.mentor.dto.MentorSaveInfoDto
import team.themoment.gsmNetworking.domain.mentor.repository.CareerRepository
import team.themoment.gsmNetworking.domain.mentor.repository.MentorRepository
import team.themoment.gsmNetworking.domain.user.dto.UserSaveInfoDto
import team.themoment.gsmNetworking.domain.user.service.UserRegistrationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 멘토를 저장하는 로직이 담긴 서비스 클래스 입니다.
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class MentorRegistrationService(
    private val userRegistrationService: UserRegistrationService,
    private val mentorRepository: MentorRepository,
    private val careerRepository: CareerRepository,
) {

    /**
     * 멘토의 정보를 저장한다.
     * 현재 단계에서는 멘티의 정보를 저장하고 있지 않기 때문에 바로 이 메서드에서 user 정보와 멘티 정보를 저장한다.
     */
    fun execute(dto: MentorSaveInfoDto) {
        val userRegistrationDto = UserSaveInfoDto(
            name = dto.name,
            generation = dto.generation,
            phoneNumber = dto.phoneNumber,
            email = dto.email,
            snsUrl = dto.snsUrl,
            profileUrl = dto.profileUrl
        )
        val user = userRegistrationService.execute(userRegistrationDto)
        val mentor = Mentor(registered = true, user = user)
        val careerList = dto.career.map {
            Career(
                mentor = mentor,
                companyName = it.companyName,
                companyUrl = it.companyUrl ?: "",
                position = it.position,
                startDate = it.startDate,
                endDate = it.endDate,
                isWorking = it.isWorking ?: false
            )
        }
        mentorRepository.save(mentor)
        careerRepository.saveAll(careerList)
    }

}
