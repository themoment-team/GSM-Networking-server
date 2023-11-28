package team.themoment.gsmNetworking.domain.gwangya.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import team.themoment.gsmNetworking.domain.gwangya.domain.QGwangya.gwangya
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostsDto

class GwangyaCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : GwangyaCustomRepository {

    override fun findPagebyCursorId(cursorId: Long, pageSize: Int): List<GwangyaPostsDto> {
        val query = queryFactory
            .select(
                Projections.constructor(
                    GwangyaPostsDto::class.java,
                    gwangya.gwangyaId,
                    gwangya.content,
                    gwangya.createdAt
                )
            )
            .from(gwangya)

        if (cursorId > 0L) {
            query
                .where(
                    ltCursorId(cursorId),
                )
        }

        return query
            .orderBy(gwangya.gwangyaId.desc())
            .limit(pageSize.toLong())
            .fetch();
    }

    private fun ltCursorId(cursorId: Long): BooleanExpression =
        gwangya.gwangyaId.lt(cursorId)
}
