package team.themoment.gsmNetworking.domain.mentor.domain

import team.themoment.gsmNetworking.common.domain.BaseIdTimestampEntity
import team.themoment.gsmNetworking.domain.user.domain.User
import javax.persistence.*

/**
 * 멘토 정보를 저장하는 Entity 클래스 입니다.
 */
@Entity
@Table(name = "mentor")
class Mentor(
    @Column(nullable = false, name = "registered")
    val registered: Boolean,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User
) : BaseIdTimestampEntity()
