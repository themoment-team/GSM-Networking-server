package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.stereotype.Service
import team.themoment.gsmNetworking.domain.mentor.dto.ExistingMentorListDto
import team.themoment.gsmNetworking.thirdParty.firebase.repository.FirebaseManager

@Service
class ExistingMentorListService (
    private val firebaseManager: FirebaseManager
) {
    fun execute(userName: String): ExistingMentorListDto {
        val existingMentors = firebaseManager.findByUserName(userName)
        return ExistingMentorListDto(existingMentors.toList())
    }
}
