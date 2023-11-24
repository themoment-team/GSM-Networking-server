package team.themoment.gsmNetworking.domain.gwangya.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Gwangya(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val gwangyaId: Long = 0,

    @Column(name = "content", nullable = false, length = 200)
    val content: String,

    @Column(name = "generation_time", nullable = false)
    val generationTime: LocalDateTime
)
