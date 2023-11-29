package team.themoment.gsmNetworking.domain.mentor.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.mentor.domain.Career
import team.themoment.gsmNetworking.domain.mentor.domain.Mentor
import team.themoment.gsmNetworking.domain.mentor.dto.MentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.MentorRegistrationDto
import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.repository.CareerRepository
import team.themoment.gsmNetworking.domain.mentor.repository.MentorRepository
import team.themoment.gsmNetworking.domain.mentor.repository.TempMentorRepository
import team.themoment.gsmNetworking.domain.mentor.service.*
import team.themoment.gsmNetworking.domain.user.dto.UserRegistrationDto
import team.themoment.gsmNetworking.domain.user.service.UserRegistrationService

@Service
class MentorService(
    private val userRegistrationService: UserRegistrationService,
    private val mentorRepository: MentorRepository,
    private val careerRepository: CareerRepository,
    private val tempMentorRepository: TempMentorRepository,
    private val queryTempMentorLIstService: QueryTempMentorListService
) : DeleteTempMentorService, MentorRegistrationService, QueryAllMentorsService {

    override fun deleteTempMentorExecute(id: Long) {
        val tempMentor = tempMentorRepository.findByIdOrNull(id)
            ?: throw ExpectedException("존재하지 않는 id 입니다.", HttpStatus.NOT_FOUND)
        tempMentor.deleted = true
    }

    override fun mentorRegistrationExecute(dto: MentorRegistrationDto) {
        val userRegistrationDto = UserRegistrationDto(
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

    override fun queryAllMentorsExecute(): List<MentorInfoDto> {
        val allMentors =
            (mentorRepository.findAllMentorInfoDto() + queryTempMentorLIstService.execute()
                .map(TempMentorInfoDto::toMentorInfoDto))
                .distinctBy { Pair(it.generation, it.name) }
                .sortedWith(compareBy({ !it.registered }, { it.position }, { it.generation }, { it.name }))

        return generateNewMentorIds(allMentors)
    }

    private fun generateNewMentorIds(allMentors: List<MentorInfoDto>): List<MentorInfoDto> =
        allMentors.mapIndexed { newMentorId, mentorInfo -> mentorInfo.copy(id = newMentorId + 1L) }
}