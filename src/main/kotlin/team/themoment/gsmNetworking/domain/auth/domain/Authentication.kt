package team.themoment.gsmNetworking.domain.auth.domain

import team.themoment.gsmNetworking.common.domain.BaseEntity
import javax.persistence.*

/**
 * 권한 정보를 저장하는 Entity 클래스 입니다.
 */
@Entity
class Authentication(
    override val id: Long = 0,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false, unique = true)
    val providerId: String,

    @Enumerated(EnumType.STRING)
    val authority: Authority
) : BaseEntity(id)
