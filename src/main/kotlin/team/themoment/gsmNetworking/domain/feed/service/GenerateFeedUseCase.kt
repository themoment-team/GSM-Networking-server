package team.themoment.gsmNetworking.domain.feed.service

import team.themoment.gsmNetworking.domain.feed.dto.FeedInfoDto
import team.themoment.gsmNetworking.domain.feed.dto.FeedSaveDto

interface GenerateFeedUseCase {
    fun generateFeed(feedSaveDto: FeedSaveDto, authenticationId: Long): FeedInfoDto
}
