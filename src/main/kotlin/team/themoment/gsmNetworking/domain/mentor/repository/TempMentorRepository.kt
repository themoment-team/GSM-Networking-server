package team.themoment.gsmNetworking.domain.mentor.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.mentor.domain.TempMentor

/**
 * TempMentor Entity를 위한 Repository 인터페이스 입니다.
 */
interface TempMentorRepository : CrudRepository<TempMentor, Long> {

    fun findByNameContaining(name: String): List<TempMentor>

    fun findByFirebaseId(firebaseId: String): TempMentor?

}
