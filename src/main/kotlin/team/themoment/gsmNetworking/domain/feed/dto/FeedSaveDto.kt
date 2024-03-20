package team.themoment.gsmNetworking.domain.feed.dto

import team.themoment.gsmNetworking.domain.feed.domain.Category
import javax.persistence.EnumType
import javax.persistence.Enumerated

data class FeedSaveDto (
    val title: String,
    val content: String,
    @Enumerated(EnumType.STRING)
    val category: Category
)