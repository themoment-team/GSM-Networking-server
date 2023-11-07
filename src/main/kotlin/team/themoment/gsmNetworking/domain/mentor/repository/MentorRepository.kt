package team.themoment.gsmNetworking.domain.mentor.repository

import team.themoment.gsmNetworking.domain.mentor.domain.Mentor
import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.user.domain.User

/**
 * Mentor Entity를 위한 Repository 인터페이스 입니다.
 */
interface MentorRepository : CrudRepository<Mentor, Long>, MentorCustomRepository {

    fun deleteByUser(user: User)
    fun findByUser(user: User): Mentor?

}
