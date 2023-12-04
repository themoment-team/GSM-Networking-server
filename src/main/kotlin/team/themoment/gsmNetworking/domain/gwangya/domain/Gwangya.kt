package team.themoment.gsmNetworking.domain.gwangya.domain

import team.themoment.gsmNetworking.common.domain.BaseTimeEntity
import javax.persistence.*

@Entity
class Gwangya(
    @Column(name = "content", nullable = false, length = 200)
    val content: String,
) : BaseTimeEntity()
