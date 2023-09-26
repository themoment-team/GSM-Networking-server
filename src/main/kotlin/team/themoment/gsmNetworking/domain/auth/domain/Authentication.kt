package team.themoment.gsmNetworking.domain.auth.domain

import javax.persistence.*

/**
 * 권한 정보를 저장하는 Entity 클래스 입니다.
 */
@Entity
class Authentication(
    @Id
    val email: String,
    @Column(name = "provider_id", nullable = false)
    val providerId: String,
    @Enumerated(EnumType.STRING)
    val authority: Authority
)