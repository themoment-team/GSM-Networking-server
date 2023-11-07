package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.auth.domain.Authority
import team.themoment.gsmNetworking.domain.mentor.repository.CareerRepository
import team.themoment.gsmNetworking.domain.mentor.repository.MentorRepository
import team.themoment.gsmNetworking.domain.user.repository.UserRepository

@Service
@Transactional(rollbackFor = [Exception::class])
class DeleteMyMentorInfoService(
    private val userRepository: UserRepository,
    private val mentorRepository: MentorRepository,
    private val careerRepository: CareerRepository,
    private val authenticatedUserManager: AuthenticatedUserManager
) {

    fun execute(authenticationId: Long) {
        val user = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("존재하지 않는 user입니다.", HttpStatus.NOT_FOUND)
        val mentor = mentorRepository.findByUser(user)
            ?: throw ExpectedException("존재하지 않는 mentor입니다.", HttpStatus.NOT_FOUND)

        careerRepository.deleteByMentor(mentor)
        mentorRepository.deleteByUser(user)
        userRepository.deleteById(user.userId)
        authenticatedUserManager.updateAuthority(Authority.TEMP_USER)
    }

}