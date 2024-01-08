package team.themoment.gsmNetworking.domain.mentor.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.mentor.domain.Career
import team.themoment.gsmNetworking.domain.mentor.domain.Mentor
import team.themoment.gsmNetworking.domain.mentor.dto.*
import team.themoment.gsmNetworking.domain.mentor.repository.CareerRepository
import team.themoment.gsmNetworking.domain.mentor.repository.MentorRepository
import team.themoment.gsmNetworking.domain.mentor.service.*
import team.themoment.gsmNetworking.domain.user.dto.UserSaveInfoDto
import team.themoment.gsmNetworking.domain.user.repository.UserRepository
import team.themoment.gsmNetworking.domain.user.service.GenerateUserUseCase
import team.themoment.gsmNetworking.domain.user.service.ModifyMyUserInfoUseCase

/**
 * 멘토 관련 로직이 담긴 클래스 입니다.
 */
@Service
class MentorService(
    private val mentorRepository: MentorRepository,
    private val careerRepository: CareerRepository,
    private val userRepository: UserRepository,
    private val queryAllTempMentorsUseCase: QueryAllTempMentorsUseCase,
    private val generateUserUseCase: GenerateUserUseCase,
    private val modifyMyUserInfoUseCase: ModifyMyUserInfoUseCase
) : QueryAllMentorsUseCase,
    MentorRegistrationUseCase,
    QueryMyMentorInfoUseCase,
    DeleteMyMentorInfoUseCase,
    ModifyMyMentorInfoUseCase {

    /**
     * 모든 멘토 리스트를 가져와서 리턴해주는 메서드 입니다.
     *
     * @return 모든 멘토 정보가 담긴 dto 리스트
     */
    @Transactional(readOnly = true)
    override fun queryAllMentors(): List<MentorInfoDto> {
        val allMentors =
            (mentorRepository.findAllMentorInfoDto() + queryAllTempMentorsUseCase.queryAllTempMentors()
                .map(TempMentorInfoDto::toMentorInfoDto))
                .distinctBy { Pair(it.generation, it.name) }
                .sortedWith(compareBy({ !it.registered }, { it.position }, { it.generation }, { it.name }))

        return generateNewMentorIds(allMentors)
    }

    private fun generateNewMentorIds(allMentors: List<MentorInfoDto>): List<MentorInfoDto> =
        allMentors.mapIndexed { newMentorId, mentorInfo -> mentorInfo.copy(id = newMentorId + 1L) }

    /**
     * 멘토의 정보를 저장한다.
     * 현재 단계에서는 멘티의 정보를 저장하고 있지 않기 때문에 바로 이 메서드에서 user 정보와 멘티 정보를 저장한다.
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun mentorRegistration(dto: MentorSaveInfoDto, authenticationId: Long) {
        val userSaveInfoDto = UserSaveInfoDto(
            name = dto.name,
            generation = dto.generation,
            phoneNumber = dto.phoneNumber,
            email = dto.email,
            snsUrl = dto.snsUrl
        )
        val user = generateUserUseCase.generateUser(userSaveInfoDto, authenticationId)
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

    /**
     * 자신의 멘토 정보를 가져오는 메서드입니다.
     *
     * @param authenticationId 회원의 식별자
     * @return 자신의 멘토 정보를 담은 dto
     * @throws ExpectedException 식별자로 내 멘토 정보를 찾을 수 없는 경우
     */
    @Transactional(readOnly = true)
    override fun queryMyMentorInfo(authenticationId: Long): MyMentorInfoDto =
        mentorRepository.findMyMentorInfoDto(authenticationId)
            ?: throw ExpectedException("내 멘토 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteMyMentorInfo(authenticationId: Long) {
        val user = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("존재하지 않는 user입니다.", HttpStatus.NOT_FOUND)
        val mentor = mentorRepository.findByUser(user)
            ?: throw ExpectedException("존재하지 않는 mentor입니다.", HttpStatus.NOT_FOUND)

        careerRepository.deleteByMentor(mentor)
        mentorRepository.deleteByUser(user)
        userRepository.deleteById(user.id)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun modifyMyMentorInfo(authenticationId: Long, mentorSaveInfoDto: MentorSaveInfoDto) {
        val user = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("user를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
        val mentor = mentorRepository.findByUser(user)
            ?: throw ExpectedException("mentor를 찾을 수 없습니다", HttpStatus.NOT_FOUND)

        val updateCareers = Career.ofCareers(mentorSaveInfoDto.career, mentor)

        val userSaveInfoDto = UserSaveInfoDto(
            name = mentorSaveInfoDto.name,
            generation = mentorSaveInfoDto.generation,
            phoneNumber = mentorSaveInfoDto.phoneNumber,
            email = mentorSaveInfoDto.email,
            snsUrl = mentorSaveInfoDto.snsUrl
        )

        modifyMyUserInfoUseCase.modifyMyUserInfo(mentor.user.authenticationId, userSaveInfoDto)
        careerRepository.saveAll(updateCareers)
    }
}
