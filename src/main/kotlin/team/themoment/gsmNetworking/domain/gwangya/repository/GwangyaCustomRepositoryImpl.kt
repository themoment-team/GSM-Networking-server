package team.themoment.gsmNetworking.domain.gwangya.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import team.themoment.gsmNetworking.domain.gwangya.domain.QGwangya.gwangya
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostsDto

class GwangyaCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : GwangyaCustomRepository {

    override fun findAll(cursorId: Long, pageSize: Int): List<GwangyaPostsDto> {
        return queryFactory
            .select(
                Projections.constructor(
                    GwangyaPostsDto::class.java,
                    gwangya.gwangyaId,
                    gwangya.content,
                    gwangya.createdAt
                )
            )
            .from(gwangya)
            .where(
                gtCursorId(cursorId),
            )
            .orderBy(gwangya.gwangyaId.asc())
            .limit(pageSize.toLong())
            .fetch();
    }

    private fun gtCursorId(cursorId: Long): BooleanExpression {
        return gwangya.gwangyaId.gt(cursorId)
    }
}
