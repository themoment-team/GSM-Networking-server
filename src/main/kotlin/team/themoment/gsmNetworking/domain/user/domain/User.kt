package team.themoment.gsmNetworking.domain.user.domain

import team.themoment.gsmNetworking.common.domain.BaseIdTimestampEntity
import team.themoment.gsmNetworking.domain.mentor.domain.Mentor
import team.themoment.gsmNetworking.domain.user.converter.EncryptConverter
import javax.persistence.*
import kotlin.random.Random

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
    val profileUrl: String?,

    @Column(name = "temporary_img_number")
    val temporaryImgNumber: Int = generateRandomNumber()
) : BaseIdTimestampEntity() {
    companion object {
        private fun generateRandomNumber(): Int {
            return Random.nextInt(1, 7)
        }
    }
}
