package team.themoment.gsmNetworking.domain.mentor.service.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.mentor.dto.MyMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.repository.MentorCustomRepository
import team.themoment.gsmNetworking.domain.mentor.service.QueryMyMentorService

/**
 * QueryMyMentorService의 구현체 입니다.
 */
@Service
class QueryMyMentorServiceImpl(
    private val mentorRepository: MentorCustomRepository
) : QueryMyMentorService {
    /**
     * 자신의 멘토 정보를 가져오는 메서드입니다.
     *
     * @param authenticationId 회원의 식별자
     * @return 자신의 멘토 정보를 담은 dto
     * @throws ExpectedException 식별자로 내 멘토 정보를 찾을 수 없는 경우
     */
    override fun execute(authenticationId: Long): MyMentorInfoDto =
        mentorRepository.findMyMentorInfoDto(authenticationId)
            ?: throw ExpectedException("내 멘토 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
}