package team.themoment.gsmNetworking.domain.feed.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import team.themoment.gsmNetworking.domain.feed.domain.Category
import team.themoment.gsmNetworking.domain.feed.domain.QFeed.feed
import team.themoment.gsmNetworking.domain.feed.dto.FeedInfoDto

class FeedCustomRepositoryImpl (
    private val queryFactory: JPAQueryFactory
) : FeedCustomRepository {

    override fun findPageByCursorId(cursorId: Long, pageSize: Long, category: Category?): List<FeedInfoDto> {
        return queryFactory.select(
            Projections.constructor(
                FeedInfoDto::class.java,
                feed.id,
                feed.title,
                feed.category,
                feed.author.name,
                feed.createdAt
                )
            )
            .from(feed)
            .where(eqCategory(category), feed.id.lt(cursorId))
            .orderBy(feed.id.desc())
            .limit(pageSize)
            .fetch()
    }

    override fun findPageWithRecentFeed(pageSize: Long, category: Category?): List<FeedInfoDto> {
        return queryFactory.select(
            Projections.constructor(
                FeedInfoDto::class.java,
                feed.id,
                feed.title,
                feed.category,
                feed.author.name,
                feed.createdAt
                )
            )
            .from(feed)
            .orderBy(feed.id.desc())
            .where(eqCategory(category))
            .limit(pageSize)
            .fetch()
        }

    private fun eqCategory(category: Category?): BooleanExpression? {
        if (category == null) {
            return null
        }

        return feed.category.eq(category)
    }

}
