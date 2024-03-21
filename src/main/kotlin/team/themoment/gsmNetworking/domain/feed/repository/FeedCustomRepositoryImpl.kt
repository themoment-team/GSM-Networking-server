package team.themoment.gsmNetworking.domain.feed.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import team.themoment.gsmNetworking.domain.feed.domain.QFeed.feed
import team.themoment.gsmNetworking.domain.feed.dto.FeedInfoDto

class FeedCustomRepositoryImpl (
    private val queryFactory: JPAQueryFactory
) : FeedCustomRepository {

    override fun findPageByCursorId(cursorId: Long, pageSize: Long): List<FeedInfoDto> {
        return queryFactory.select(
            Projections.constructor(
                FeedInfoDto::class.java,
                feed.id,
                feed.title,
                feed.category,
                feed.user.name,
                feed.createdAt
            )
        )
            .from(feed)
            .where(feed.id.lt(cursorId))
            .orderBy(feed.id.desc())
            .limit(pageSize)
            .fetch()
    }

    override fun findPageWithRecentFeed(pageSize: Long): List<FeedInfoDto> {
        return queryFactory.select(
            Projections.constructor(
                FeedInfoDto::class.java,
                feed.id,
                feed.title,
                feed.category,
                feed.user.name,
                feed.createdAt
            )
        )
            .from(feed)
            .orderBy(feed.id.desc())
            .limit(pageSize)
            .fetch()
    }

}