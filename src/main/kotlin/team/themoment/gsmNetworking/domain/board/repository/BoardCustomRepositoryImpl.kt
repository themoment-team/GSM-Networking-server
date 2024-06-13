package team.themoment.gsmNetworking.domain.board.repository

import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.group.GroupBy.list
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import team.themoment.gsmNetworking.domain.board.domain.QBoard.board
import team.themoment.gsmNetworking.domain.board.domain.QFile.file
import team.themoment.gsmNetworking.domain.board.dto.BoardListDto
import team.themoment.gsmNetworking.domain.board.dto.FileInfoDto
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

        val pinnedIds = getPinnedIds(pinnedPosts)

        val overridePageSize = overridePageSize(pageSize, pinnedPosts.size.toLong())

        val otherPosts = queryFactory.selectFrom(board)
            .join(board.author)
            .leftJoin(file)
            .on(board.eq(file.board))
            .where(eqCategory(boardCategory), board.isPinned.isFalse, board.id.lt(cursorId), board.id.notIn(pinnedIds))
            .orderBy(board.id.desc())
            .transform(
                groupBy(board.id).list(
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
                        list(
                            Projections.constructor(
                                FileInfoDto::class.java,
                                file.id,
                                file.fileUrl
                            )
                        )
                    )
                )
            )

        return pinnedPosts + otherPosts.subList(0, overridePageSize.toInt())
    }

    override fun findPageWithRecentBoard(
        pageSize: Long,
        boardCategory: BoardCategory?,
        user: User,
    ): List<BoardListDto> {
        val pinnedPosts = findPinnedPostsLimit3(user, boardCategory)

        if (pinnedPosts.size >= pageSize) {
            return pinnedPosts.take(pageSize.toInt())
        }

        val pinnedIds = getPinnedIds(pinnedPosts)

        val overridePageSize = overridePageSize(pageSize, pinnedPosts.size.toLong())

        val otherPosts = queryFactory.selectFrom(board)
            .join(board.author)
            .leftJoin(file)
            .on(board.eq(file.board))
            .where(eqCategory(boardCategory), board.isPinned.isFalse, board.id.notIn(pinnedIds))
            .orderBy(board.id.desc())
            .transform(
                groupBy(board.id).list(
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
                        list(
                            Projections.constructor(
                                FileInfoDto::class.java,
                                file.id,
                                file.fileUrl
                            )
                        )
                    )
                )
            )


        return pinnedPosts + otherPosts.subList(0, overridePageSize.toInt())
    }

    private fun getPinnedIds(pinnedPosts: List<BoardListDto>): List<Long> {
        return pinnedPosts.map { it.id }
    }

    private fun overridePageSize(pageSize: Long, pinnedPostsSize: Long): Long {
        return pageSize - pinnedPostsSize
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
        return queryFactory.selectFrom(board)
            .join(board.author)
            .leftJoin(file)
            .on(board.eq(file.board))
            .where(board.isPinned.isTrue, eqCategory(boardCategory))
            .orderBy(board.id.desc())
            .transform(
                groupBy(board.id).list(
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
                        list(
                            Projections.constructor(
                                FileInfoDto::class.java,
                                file.id,
                                file.fileUrl
                            )
                        )
                    )
                )
            )
    }
}
