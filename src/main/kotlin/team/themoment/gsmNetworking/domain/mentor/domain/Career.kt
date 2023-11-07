package team.themoment.gsmNetworking.domain.mentor.domain

import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
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

    @ManyToOne(fetch = FetchType.LAZY, )
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

    @Column(nullable = false)
    val endDate: LocalDate,

    @Column(nullable = false)
    val isWorking: Boolean
)
