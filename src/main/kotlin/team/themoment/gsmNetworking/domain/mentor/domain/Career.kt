package team.themoment.gsmNetworking.domain.mentor.domain

import team.themoment.gsmNetworking.domain.mentor.dto.MentorCareerDto
import java.time.LocalDate
import javax.persistence.*

/**
 * 멘토의 경력을 저장하는 Entity 클래스 입니다.
 */
@Entity
@Table(name = "career")
class Career(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val careerId: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    val mentor: Mentor,

    @Column(nullable = false)
    val companyName: String,

    @Column(nullable = true)
    val companyUrl: String,

    @Column(nullable = false)
    val position: String,

    @Column(nullable = false)
    val startDate: LocalDate,

    @Column(nullable = true)
    val endDate: LocalDate? = null,

    @Column(nullable = false)
    val isWorking: Boolean
) {
    companion object{
        fun ofCareers(mentorCareerDto: List<MentorCareerDto>, mentor: Mentor): List<Career>{
            return mentorCareerDto.map {
                Career(
                    it.id,
                    mentor,
                    it.companyName,
                    it.companyUrl ?: "",
                    it.position,
                    it.startDate,
                    it.endDate,
                    it.isWorking ?: false
                )
            }
        }
    }
}
