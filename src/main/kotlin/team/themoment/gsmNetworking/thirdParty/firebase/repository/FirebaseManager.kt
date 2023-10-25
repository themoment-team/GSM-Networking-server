package team.themoment.gsmNetworking.thirdParty.firebase.repository

import team.themoment.gsmNetworking.domain.mentor.dto.ExistingMentorDto

interface FirebaseManager {
    fun findByUserName(userName: String): List<ExistingMentorDto>
}