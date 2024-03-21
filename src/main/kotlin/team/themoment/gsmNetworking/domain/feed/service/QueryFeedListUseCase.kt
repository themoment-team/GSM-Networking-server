package team.themoment.gsmNetworking.domain.feed.service

import team.themoment.gsmNetworking.domain.feed.dto.FeedInfoDto

interface QueryFeedListUseCase {
    fun queryFeedList(cursorId: Long, pageSize: Long): List<FeedInfoDto>
}