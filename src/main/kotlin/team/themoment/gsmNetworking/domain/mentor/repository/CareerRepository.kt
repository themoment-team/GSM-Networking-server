package team.themoment.gsmNetworking.domain.mentor.repository

import team.themoment.gsmNetworking.domain.mentor.domain.Career
import org.springframework.data.repository.CrudRepository

interface CareerRepository : CrudRepository<Career, Long>
