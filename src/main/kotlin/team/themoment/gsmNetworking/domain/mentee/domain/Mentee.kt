package team.themoment.gsmNetworking.domain.mentee.domain

import team.themoment.gsmNetworking.common.domain.BaseIdTimestampEntity
import team.themoment.gsmNetworking.domain.user.domain.User
import javax.persistence.*

@Entity
@Table(name = "mentee")
class Mentee (
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User
) : BaseIdTimestampEntity()
