package team.themoment.gsmNetworking.common.domain

import javax.persistence.*

@MappedSuperclass
abstract class BaseIdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
