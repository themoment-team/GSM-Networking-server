package team.themoment.gsmNetworking.domain.feed.repository

import team.themoment.gsmNetworking.domain.feed.dto.FeedInfoDto

interface FeedCustomRepository {
    fun findPageByCursorId(cursorId: Long, pageSize: Long): List<FeedInfoDto>
    fun findPageWithRecentFeed(pageSize: Long): List<FeedInfoDto>
}