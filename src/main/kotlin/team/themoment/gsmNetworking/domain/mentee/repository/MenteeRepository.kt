package team.themoment.gsmNetworking.domain.mentee.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.mentee.domain.Mentee
import team.themoment.gsmNetworking.domain.user.domain.User

interface MenteeRepository : CrudRepository<Mentee, Long> {

    fun deleteByUser(user: User)
}
