package team.themoment.gsmNetworking.domain.feed.repository

import team.themoment.gsmNetworking.domain.feed.domain.Category
import team.themoment.gsmNetworking.domain.feed.dto.FeedInfoDto

interface FeedCustomRepository {
    fun findPageByCursorId(cursorId: Long, pageSize: Long, category: Category?): List<FeedInfoDto>
    fun findPageWithRecentFeed(pageSize: Long, category: Category?): List<FeedInfoDto>
}