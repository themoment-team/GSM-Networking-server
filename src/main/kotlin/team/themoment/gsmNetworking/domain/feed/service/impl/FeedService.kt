package team.themoment.gsmNetworking.domain.feed.service.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.feed.domain.Feed
import team.themoment.gsmNetworking.domain.feed.dto.FeedInfoDto
import team.themoment.gsmNetworking.domain.feed.dto.FeedSaveDto
import team.themoment.gsmNetworking.domain.feed.repository.FeedRepository
import team.themoment.gsmNetworking.domain.feed.service.GenerateFeedUseCase
import team.themoment.gsmNetworking.domain.feed.service.QueryFeedListUseCase
import team.themoment.gsmNetworking.domain.user.repository.UserRepository

@Service
class FeedService (
    private val feedRepository: FeedRepository,
    private val userRepository: UserRepository,
    private val authenticatedUserManager: AuthenticatedUserManager
) : GenerateFeedUseCase, QueryFeedListUseCase {

    @Transactional
    override fun generateFeed(feedSaveDto: FeedSaveDto) {
        val authenticationId = authenticatedUserManager.getName()
        val currentUser = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        val newFeed = Feed(
            title = feedSaveDto.title,
            content = feedSaveDto.content,
            category = feedSaveDto.category,
            user = currentUser
        )

        feedRepository.save(newFeed)

    }

    override fun queryFeedList(cursorId: Long, pageSize: Long): List<FeedInfoDto> =
        if (cursorId == 0L)
            feedRepository.findPageWithRecentFeed(pageSize)
        else
            feedRepository.findPageByCursorId(cursorId, pageSize)

}
