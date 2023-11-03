package team.themoment.gsmNetworking.domain.mentor.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.mentor.domain.TempMentor

interface TempMentorRepository : CrudRepository<TempMentor, Long> {

    fun findByNameContaining(name: String): List<TempMentor>

    fun deleteByFirebaseId(firebaseId: String)

}
