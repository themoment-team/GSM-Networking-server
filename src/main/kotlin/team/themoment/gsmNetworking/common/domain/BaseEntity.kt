package team.themoment.gsmNetworking.common.domain

import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}