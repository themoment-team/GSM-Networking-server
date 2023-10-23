package team.themoment.gsmNetworking.domain.mentor.service

import com.google.cloud.firestore.Firestore
import org.springframework.stereotype.Service
import team.themoment.gsmNetworking.domain.mentor.dto.ExistingMentorDto
import team.themoment.gsmNetworking.domain.mentor.dto.ExistingMentorListDto

@Service
class QueryExistingMentorListService (
    private val firestore: Firestore
) {
    fun execute(userName: String): ExistingMentorListDto {
        val mentors = firestore.collection("workers")
        val querySnapshot = mentors.whereEqualTo("name", userName).get().get()
        val existingMentors = querySnapshot.documents.map { document ->
            val SNS = document.getString("SNS")
            val company = document.get("company") as Map<*, *>
            val companyName = company["name"] as String
            val companyUrl = company["URL"] as String
            val email = document.getString("email")
            val generation = document.getLong("generation")
            val name = document.getString("name")
            val position = document.getString("position")
            ExistingMentorDto(SNS, companyName, companyUrl, email, generation, name, position)
        }

        return ExistingMentorListDto(existingMentors.toMutableList())
    }
}
