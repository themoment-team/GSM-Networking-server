package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.mentor.domain.Career
import team.themoment.gsmNetworking.domain.mentor.dto.MentorSaveInfoDto
import team.themoment.gsmNetworking.domain.mentor.repository.CareerRepository
import team.themoment.gsmNetworking.domain.mentor.repository.MentorRepository
import team.themoment.gsmNetworking.domain.user.dto.UserSaveInfoDto
import team.themoment.gsmNetworking.domain.user.repository.UserRepository
import team.themoment.gsmNetworking.domain.user.service.ModifyMyUserInfoService

@Service
@Transactional(rollbackFor = [Exception::class])
class ModifyMyMentorInfoService(
    private val careerRepository: CareerRepository,
    private val mentorRepository: MentorRepository,
    private val userRepository: UserRepository,
    private val modifyMyUserInfoService: ModifyMyUserInfoService
) {

    fun execute(authenticationId: Long, mentorSaveInfoDto: MentorSaveInfoDto) {
        val user = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("user를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
        val mentor = mentorRepository.findByUser(user)
            ?: throw ExpectedException("mentor를 찾을 수 없습니다", HttpStatus.NOT_FOUND)

        careerRepository.deleteAllByMentor(mentor)
        val updateCareers = Career.ofCareers(mentorSaveInfoDto.career, mentor)

        val userSaveInfoDto = UserSaveInfoDto(
            name = mentorSaveInfoDto.name,
            generation = mentorSaveInfoDto.generation,
            phoneNumber = mentorSaveInfoDto.phoneNumber,
            email = mentorSaveInfoDto.email,
            snsUrl = mentorSaveInfoDto.snsUrl,
            profileUrl = mentorSaveInfoDto.profileUrl
        )

        modifyMyUserInfoService.execute(mentor.user.authenticationId, userSaveInfoDto)
        careerRepository.saveAll(updateCareers)
    }
}
