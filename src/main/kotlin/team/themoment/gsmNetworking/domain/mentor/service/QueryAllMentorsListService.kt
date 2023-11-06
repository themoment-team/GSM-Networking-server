package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.mentor.dto.MentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.repository.MentorCustomRepository

/**
 * 멘토의 정보 리스트로 볼 수 있는 로직이 담긴 클래스 입니다.
 */
@Service
@Transactional(readOnly = true)
class QueryAllMentorsListService(
    private val mentorRepository: MentorCustomRepository,
    private val queryTempMentorListService: QueryTempMentorListService,
) {

    /**
     * 모든 멘토 리스트를 가져와서 리턴해주는 메서드 입니다.
     *
     * @return 모든 멘토 정보가 담긴 dto 리스트
     */
    fun execute(): List<MentorInfoDto> {
        val allMentors = (
                mentorRepository.findAllMentorInfoDto() +
                        queryTempMentorListService.execute().map(TempMentorInfoDto::toMentorInfoDto)
                ).distinctBy { Pair(it.generation, it.name) }

        increaseMentorIdCount(allMentors)

        return allMentors
    }

    private fun increaseMentorIdCount(allMentors: List<MentorInfoDto>): List<MentorInfoDto> {
        val newId = 1L
        allMentors.forEach { mentorInfo ->
            mentorInfo.id = newId.inc()
        }
        return allMentors
    }

}
