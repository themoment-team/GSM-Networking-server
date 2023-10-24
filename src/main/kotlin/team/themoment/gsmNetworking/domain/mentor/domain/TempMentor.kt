package team.themoment.gsmNetworking.domain.mentor.domain

import javax.persistence.*

@Entity
@Table(name = "temp_mentor")
class TempMentor(
    @Id
    @Column(name = "real_id")
    val realId: Int,

    @Column(nullable = false, name = "id", unique = true)
    val firebaseId: String,

    @Column(nullable = false, name = "name")
    val name: String,

    @Column(nullable = false, name = "generation")
    val generation: Int,

    @Column(nullable = true, name = "email")
    val email: String?,

    @Column(nullable = true, name = "sns")
    val sns: String?,

    @Column(nullable = true, name = "company_url")
    val companyUrl: String?,

    @Column(nullable = false, name = "company_name")
    val companyName: String,

    @Column(nullable = false, name = "position")
    val position: String
)
