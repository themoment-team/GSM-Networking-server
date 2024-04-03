package team.themoment.gsmNetworking.domain.mentor.domain

import team.themoment.gsmNetworking.common.domain.BaseIdTimestampEntity
import team.themoment.gsmNetworking.domain.user.domain.User
import javax.persistence.*
import kotlin.random.Random

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
    val user: User,

    @Column(name = "temporary_img_number")
    val temporaryImgNumber: Int
) : BaseIdTimestampEntity() {

    constructor(registered: Boolean, user: User) : this(registered, user, generateRandomNumber())

    companion object {
        private fun generateRandomNumber(): Int {
            return Random.nextInt(0, 9)
        }
    }

}
