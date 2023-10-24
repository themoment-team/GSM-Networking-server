package team.themoment.gsmNetworking.thirdParty.firebase.repository.impl

import com.google.cloud.firestore.Firestore
import org.springframework.stereotype.Repository
import team.themoment.gsmNetworking.domain.mentor.dto.ExistingMentorDto
import team.themoment.gsmNetworking.domain.mentor.dto.ExistingMentorListDto
import team.themoment.gsmNetworking.thirdParty.firebase.repository.FirebaseRepository

@Repository
class FirebaseRepositoryImpl(
    private val firestore: Firestore
): FirebaseRepository {

    override fun findByUserName(userName: String): List<ExistingMentorDto> {
        val mentors = firestore.collection("workers")
        val querySnapshot = mentors.whereEqualTo("name", userName).get().get()
        val existingMentors = querySnapshot.documents.map { document ->
            val mentorData = document.toObject(ExistingMentorDto::class.java)
            ExistingMentorDto(
                mentorData.name,
                mentorData.generation,
                mentorData.email,
                mentorData.position,
                mentorData.company,
                mentorData.SNS
            )
        }
        return existingMentors
    }
}