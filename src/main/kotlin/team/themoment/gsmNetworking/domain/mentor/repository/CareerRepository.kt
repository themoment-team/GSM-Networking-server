package team.themoment.gsmNetworking.domain.mentor.repository

import team.themoment.gsmNetworking.domain.mentor.domain.Career
import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.mentor.domain.Mentor

/**
 * Career Entity를 위한 Repository 인터페이스 입니다.
 */
interface CareerRepository : CrudRepository<Career, Long> {

    fun deleteByMentor(mentor: Mentor)

    fun deleteAllByMentor(mentor: Mentor)
}
