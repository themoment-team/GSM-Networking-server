package team.themoment.gsmNetworking.domain.mentor.repository

import team.themoment.gsmNetworking.domain.mentor.domain.Career
import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.mentor.domain.Mentor

interface CareerRepository : CrudRepository<Career, Long> {

    fun deleteByMentor(mentor: Mentor)

}
