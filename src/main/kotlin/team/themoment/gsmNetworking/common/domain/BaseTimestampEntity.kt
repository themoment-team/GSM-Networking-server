package team.themoment.gsmNetworking.common.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimestampEntity {
    @CreatedDate
    @Column(
        name = "created_at",
        nullable = false,
        updatable = false,
    )
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column(
        name = "updated_at",
        nullable = false,
        updatable = true,
    )
    var updatedAt: LocalDateTime = LocalDateTime.now()
}
