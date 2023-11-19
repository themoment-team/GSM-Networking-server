package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.mentor.dto.ProfileMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.repository.MentorCustomRepository

/**
 * 자신의 멘토 정보를 가져오는 로직이 담긴 서비스입니다.
 */
@Service
class QueryMyMentorService(
    private val mentorRepository: MentorCustomRepository
) {

    /**
     * 자신의 멘토 정보를 가져오는 메서드입니다.
     *
     * @param authenticationId 회원의 식별자
     * @return 자신의 멘토 정보를 담은 dto
     * @throws ExpectedException 식별자로 내 멘토 정보를 찾을 수 없는 경우
     */
    fun execute(authenticationId: Long): ProfileMentorInfoDto =
        mentorRepository.findMyMentorInfoDto(authenticationId)
            ?: throw ExpectedException("내 멘토 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
}