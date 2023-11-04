package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.mentor.repository.MentorRepository
import team.themoment.gsmNetworking.domain.user.repository.UserRepository

@Service
@Transactional(rollbackFor = [Exception::class])
class DeleteMyMentorInfoService(
    private val userRepository: UserRepository,
    private val mentorRepository: MentorRepository
) {

    fun execute(authenticationId: Long) {
        val user = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("존재하지 않는 user입니다.", HttpStatus.NOT_FOUND)
        mentorRepository.deleteByUser(user)
    }

}