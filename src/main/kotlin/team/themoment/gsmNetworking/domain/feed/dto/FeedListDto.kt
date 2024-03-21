package team.themoment.gsmNetworking.domain.feed.dto

data class FeedListDto (
    val hasNext: Boolean,
    val feedList: List<FeedInfoDto>
)