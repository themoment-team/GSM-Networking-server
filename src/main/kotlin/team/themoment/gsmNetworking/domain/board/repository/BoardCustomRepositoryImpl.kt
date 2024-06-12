package team.themoment.gsmNetworking.domain.board.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import team.themoment.gsmNetworking.domain.board.domain.QBoard.board
import team.themoment.gsmNetworking.domain.board.dto.BoardListDto
import team.themoment.gsmNetworking.domain.board.dto.FileUrlsDto
import team.themoment.gsmNetworking.domain.comment.dto.AuthorDto
import team.themoment.gsmNetworking.domain.like.domain.QLike.like
import team.themoment.gsmNetworking.domain.user.domain.User


class BoardCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : BoardCustomRepository {

    override fun findPageByCursorId(
        cursorId: Long,
        pageSize: Long,
        boardCategory: BoardCategory?,
        user: User,
    ): List<BoardListDto> {
        val pinnedPosts = findPinnedPostsLimit3(user, boardCategory)

        if (pinnedPosts.size >= pageSize) {
            return pinnedPosts.take(pageSize.toInt())
        }

        val pinnedIds = pinnedPosts.map { it.id }

        val overridePageSize = (pageSize - pinnedPosts.size)

        val otherPosts = queryFactory.select(
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
                likeCase(user),
                board.isPinned,
                Projections.constructor(
                    FileUrlsDto::class.java,
                    board.fileUrls
                )
            )
        )
            .from(board)
            .where(eqCategory(boardCategory), board.id.lt(cursorId), board.isPinned.isFalse, board.id.notIn(pinnedIds))
            .orderBy(board.id.desc())
            .limit(overridePageSize)
            .fetch()

        return pinnedPosts + otherPosts
    }

    override fun findPageWithRecentBoard(
        pageSize: Long,
        boardCategory: BoardCategory?,
        user: User,
    ): List<BoardListDto> {
        val pinnedPosts = findPinnedPostsLimit3(user, boardCategory)

        val pinnedIds = pinnedPosts.map { it.id }

        val otherPosts = queryFactory.select(
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
                likeCase(user),
                board.isPinned,
                Projections.constructor(
                    FileUrlsDto::class.java,
                    board.fileUrls
                )
            )
        )
            .from(board)
            .orderBy(board.id.desc())
            .where(eqCategory(boardCategory), board.isPinned.isFalse, board.id.notIn(pinnedIds))
            .limit(pageSize)
            .fetch()

        return pinnedPosts + otherPosts
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

    private fun findPinnedPostsLimit3(user: User, boardCategory: BoardCategory?): List<BoardListDto> {
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
                likeCase(user),
                board.isPinned,
            )
        )
            .from(board)
            .where(board.isPinned.isTrue, eqCategory(boardCategory))
            .orderBy(board.id.desc())
            .limit(3)
            .fetch()

    }

}
