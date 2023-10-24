package team.themoment.gsmNetworking.thirdParty.firebase.repository

import team.themoment.gsmNetworking.domain.mentor.dto.ExistingMentorDto

interface FirebaseRepository {
    fun findByUserName(userName: String): List<ExistingMentorDto>
}