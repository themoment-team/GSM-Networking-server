package team.themoment.gsmNetworking.domain.board.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import team.themoment.gsmNetworking.domain.board.domain.Category
import team.themoment.gsmNetworking.domain.board.domain.QBoard.board
import team.themoment.gsmNetworking.domain.board.dto.BoardInfoDto

class BoardCustomRepositoryImpl (
    private val queryFactory: JPAQueryFactory
) : BoardCustomRepository {

    override fun findPageByCursorId(cursorId: Long, pageSize: Long, category: Category?): List<BoardInfoDto> {
        return queryFactory.select(
            Projections.constructor(
                BoardInfoDto::class.java,
                board.id,
                board.title,
                board.category,
                board.author.name,
                board.createdAt
                )
            )
            .from(board)
            .where(eqCategory(category), board.id.lt(cursorId))
            .orderBy(board.id.desc())
            .limit(pageSize)
            .fetch()
    }

    override fun findPageWithRecentBoard(pageSize: Long, category: Category?): List<BoardInfoDto> {
        return queryFactory.select(
            Projections.constructor(
                BoardInfoDto::class.java,
                board.id,
                board.title,
                board.category,
                board.author.name,
                board.createdAt
                )
            )
            .from(board)
            .orderBy(board.id.desc())
            .where(eqCategory(category))
            .limit(pageSize)
            .fetch()
        }

    private fun eqCategory(category: Category?): BooleanExpression? {
        if (category == null) {
            return null
        }

        return board.category.eq(category)
    }

}
