package team.themoment.gsmNetworking.domain.mentor.domain

import team.themoment.gsmNetworking.common.domain.BaseEntity
import java.time.LocalDate
import javax.persistence.*

/**
 * 멘토의 경력을 저장하는 Entity 클래스 입니다.
 */
@Entity
@Table(name = "career")
class Career(
    override val id: Long = 0,

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
) : BaseEntity(id)
