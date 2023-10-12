package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.mentor.dto.MentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.repository.MentorCustomRepository

/**
 * 멘토의 정보 리스트로 볼 수 있는 로직이 담긴 클래스 입니다.
 */
@Service
@Transactional(readOnly = true)
class QueryMentorListService(
    private val mentorRepository: MentorCustomRepository
) {

    /**
     * 멘토 리스트를 가져와서 리턴해주는 메서드 입니다.
     *
     * @return 멘토 정보가 담긴 dto 리스트
     */
    fun execute(): List<MentorInfoDto> {
        return mentorRepository.findAllMentorInfoDto()
    }

}
