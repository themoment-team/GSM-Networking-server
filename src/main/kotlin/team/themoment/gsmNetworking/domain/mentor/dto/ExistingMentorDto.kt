package team.themoment.gsmNetworking.domain.mentor.dto

data class ExistingMentorDto (
    val SNS: String,
    val companyName: String,
    val companyUrl: String,
    val email: String,
    val generation: Int,
    val name: String,
    val position: String
)
