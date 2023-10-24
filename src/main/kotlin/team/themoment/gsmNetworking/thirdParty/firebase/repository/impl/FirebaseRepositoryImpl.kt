package team.themoment.gsmNetworking.thirdParty.firebase.repository.impl

import com.google.cloud.firestore.Firestore
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.domain.mentor.dto.ExistingMentorDto
import team.themoment.gsmNetworking.thirdParty.firebase.repository.FirebaseManager

@Component
class FirebaseManagerImpl(
    private val firestore: Firestore
): FirebaseManager {

    override fun findByUserName(userName: String): List<ExistingMentorDto> {
        val mentors = firestore.collection("workers")
        val querySnapshot = mentors.whereEqualTo("name", userName).get().get()
        val existingMentors = querySnapshot.documents.map { document ->
            val mentorData = document.toObject(ExistingMentorDto::class.java)
            ExistingMentorDto(
                mentorData.name,
                mentorData.email,
                mentorData.generation,
                mentorData.position,
                mentorData.company,
                mentorData.SNS
            )
        }
        return existingMentors
    }
}