package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.auth.domain.Authority
import team.themoment.gsmNetworking.domain.mentor.domain.Mentor
import team.themoment.gsmNetworking.domain.mentor.domain.TempMentor
import team.themoment.gsmNetworking.domain.mentor.repository.CareerRepository
import team.themoment.gsmNetworking.domain.mentor.repository.MentorRepository
import team.themoment.gsmNetworking.domain.mentor.repository.TempMentorRepository
import team.themoment.gsmNetworking.domain.user.domain.User
import team.themoment.gsmNetworking.domain.user.repository.UserRepository

/**
 * 자신의 멘토 정보를 삭제하는 로직이 담긴 클래스입니다.
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class DeleteMyMentorInfoService(
    private val userRepository: UserRepository,
    private val mentorRepository: MentorRepository,
    private val careerRepository: CareerRepository,
    private val tempMentorRepository: TempMentorRepository,
    private val authenticatedUserManager: AuthenticatedUserManager
) {

    /**
     * 자신의 멘토 정보를 삭제합니다.
     *
     * @param authenticationId 회원의 식별자
     */
    fun execute(authenticationId: Long) {
        val user = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("존재하지 않는 user입니다.", HttpStatus.NOT_FOUND)
        val mentor = mentorRepository.findByUser(user)
            ?: throw ExpectedException("존재하지 않는 mentor입니다.", HttpStatus.NOT_FOUND)
        tempMentorRepository.findByGenerationAndName(user.generation, user.name)
            ?.let { it.deleted = false }

        deleteMyInfo(user, mentor)
    }

    private fun deleteMyInfo(user: User, mentor: Mentor) {
        careerRepository.deleteByMentor(mentor)
        mentorRepository.deleteByUser(user)
        userRepository.deleteById(user.userId)
        authenticatedUserManager.updateAuthority(Authority.TEMP_USER)
    }

}