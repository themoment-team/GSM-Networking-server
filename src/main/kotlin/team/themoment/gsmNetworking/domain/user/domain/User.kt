package team.themoment.gsmNetworking.domain.user.domain

import team.themoment.gsmNetworking.common.domain.BaseIdEntity
import team.themoment.gsmNetworking.domain.user.converter.EncryptConverter
import javax.persistence.*

/**
 * 사용자의 정보를 저장하는 Entity 클래스 입니다.
 */
@Entity
@Table(name = "user")
class User(
    override val id: Long = 0,

    @Column(nullable = false, unique = true)
    val authenticationId: Long,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val generation: Int,

    @Column(nullable = false, unique = true)
    val email: String,

    @Convert(converter = EncryptConverter::class)
    @Column(nullable = false, unique = true)
    val phoneNumber: String,

    @Column(nullable = true)
    val snsUrl: String?,

    @Column(nullable = true)
    val profileUrl: String?
) : BaseIdEntity()
