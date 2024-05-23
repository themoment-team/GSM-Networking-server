package team.themoment.gsmNetworking.domain.user.domain

import team.themoment.gsmNetworking.common.domain.BaseIdTimestampEntity
import team.themoment.gsmNetworking.domain.board.domain.Board
import team.themoment.gsmNetworking.domain.comment.domain.Comment
import team.themoment.gsmNetworking.domain.like.domain.Like
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
    var profileUrl: String?,

    @Column(name = "default_img_number")
    var defaultImgNumber: Int = 0,

    @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], orphanRemoval = true)
    val boards: MutableList<Board> = ArrayList(),

    @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: MutableList<Comment> = ArrayList(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val likes: MutableList<Like> = ArrayList()

) : BaseIdTimestampEntity() {

    init {
        defaultImgNumber = generateRandomNumber()
    }

    companion object {
        private fun generateRandomNumber(): Int {
            return Random.nextInt(0, 6)
        }
    }

    fun updateProfileNumber(defaultImgNumber: Int) {
        this.defaultImgNumber = defaultImgNumber

        if (this.profileUrl != null) {
            this.profileUrl = null
        }
    }

}
