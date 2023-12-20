package team.themoment.gsmNetworking.domain.mentee.repository

import org.springframework.data.repository.CrudRepository
import team.themoment.gsmNetworking.domain.mentee.domain.Mentee

interface MenteeRepository : CrudRepository<Mentee, Long>
