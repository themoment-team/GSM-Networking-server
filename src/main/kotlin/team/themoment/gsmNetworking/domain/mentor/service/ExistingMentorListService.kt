package team.themoment.gsmNetworking.domain.mentor.service

import org.springframework.stereotype.Service
import team.themoment.gsmNetworking.domain.mentor.dto.ExistingMentorListDto
import team.themoment.gsmNetworking.thirdParty.firebase.repository.FirebaseRepository

@Service
class ExistingMentorListService (
    private val firebaseRepository: FirebaseRepository
) {
    fun execute(userName: String): ExistingMentorListDto {
        val existingMentors = firebaseRepository.findByUserName(userName)
        return ExistingMentorListDto(existingMentors.toList())
    }
}
