package team.themoment.gsmNetworking.thirdParty.firebase.repository.impl

import com.google.cloud.firestore.Firestore
import team.themoment.gsmNetworking.domain.mentor.dto.ExistingMentorDto
import team.themoment.gsmNetworking.thirdParty.firebase.repository.FirebaseRepository

class FirebaseRepositoryImpl(
    private val firestore: Firestore
): FirebaseRepository {

    override fun findByUserName(userName: String): List<ExistingMentorDto> {
        val mentors = firestore.collection("workers")
        val querySnapshot = mentors.whereEqualTo("name", userName).get().get()
        val existingMentors = querySnapshot.documents.map { document ->
            val sns = document.getString("SNS")
            val company = document.get("company") as Map<*, *>
            val companyName = company["name"] as String
            val companyUrl = company["URL"] as String
            val email = document.getString("email")
            val generation = document.getLong("generation")
            val name = document.getString("name")
            val position = document.getString("position")
            ExistingMentorDto(name, generation, email, position, companyName, companyUrl, sns)
        }
        return existingMentors
    }
}