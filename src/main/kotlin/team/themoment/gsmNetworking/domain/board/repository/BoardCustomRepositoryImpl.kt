package team.themoment.gsmNetworking.domain.board.repository

import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import team.themoment.gsmNetworking.domain.board.domain.QBoard
import team.themoment.gsmNetworking.domain.board.domain.QBoard.board
import team.themoment.gsmNetworking.domain.board.dto.BoardListDto
import team.themoment.gsmNetworking.domain.comment.dto.AuthorDto
import team.themoment.gsmNetworking.domain.like.domain.QLike
import team.themoment.gsmNetworking.domain.like.domain.QLike.like
import team.themoment.gsmNetworking.domain.user.domain.User


class BoardCustomRepositoryImpl (
    private val queryFactory: JPAQueryFactory
) : BoardCustomRepository {

    override fun findPageByCursorId(cursorId: Long, pageSize: Long, boardCategory: BoardCategory?, user: User): List<BoardListDto> {
        return queryFactory.select(
            Projections.constructor(
                BoardListDto::class.java,
                    board.id,
                    board.title,
                    board.boardCategory,
                    Projections.constructor(
                    AuthorDto::class.java,
                        board.author.name,
                        board.author.generation,
                        board.author.profileUrl,
                        board.author.defaultImgNumber
                    ),
                    board.createdAt,
                    board.comments.size(),
                    board.likes.size(),
                    likeCase(user)
                )
            )
            .from(board)
            .where(eqCategory(boardCategory), board.id.lt(cursorId))
            .orderBy(board.id.desc())
            .limit(pageSize)
            .fetch()
    }

    override fun findPageWithRecentBoard(pageSize: Long, boardCategory: BoardCategory?, user: User): List<BoardListDto> {
        return queryFactory.select(
                Projections.constructor(
                    BoardListDto::class.java,
                    board.id,
                    board.title,
                    board.boardCategory,
                    Projections.constructor(
                        AuthorDto::class.java,
                        board.author.name,
                        board.author.generation,
                        board.author.profileUrl,
                        board.author.defaultImgNumber
                    ),
                    board.createdAt,
                    board.comments.size(),
                    board.likes.size(),
                    likeCase(user)
                )
            )
            .from(board)
            .orderBy(board.id.desc())
            .where(eqCategory(boardCategory))
            .limit(pageSize)
            .fetch()
        }

    private fun eqCategory(boardCategory: BoardCategory?): BooleanExpression? =
        boardCategory?.let { board.boardCategory.eq(it) }

    private fun likeCase(user: User): BooleanExpression =
        JPAExpressions.selectOne()
            .from(like)
            .where(
                like.board.eq(board),
                like.user.eq(user)
            )
            .exists()

}
