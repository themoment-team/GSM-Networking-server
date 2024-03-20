package team.themoment.gsmNetworking.domain.feed.domain

import team.themoment.gsmNetworking.common.domain.BaseIdTimestampEntity
import team.themoment.gsmNetworking.domain.user.domain.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "feed")
class Feed (
    @Column(name = "title")
    val title: String,

    @Column(name = "content")
    val content: String,

    @OneToOne
    @JoinColumn(name = "user_id")
    val user: User
): BaseIdTimestampEntity();
