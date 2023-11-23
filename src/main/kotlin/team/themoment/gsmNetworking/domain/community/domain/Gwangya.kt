package team.themoment.gsmNetworking.domain.community.domain

import team.themoment.gsmNetworking.common.util.UUIDUtils
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Gwangya(
    @Id
    @Column(name = "gwangya_id", columnDefinition = "BINARY(16)")
    val gwangyaId: UUID = UUIDUtils.generateUUIDv7(),

    @Column(name = "content", nullable = false, length = 200)
    val content: String,
)
