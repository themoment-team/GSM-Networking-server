package team.themoment.gsmNetworking.domain.popup.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import team.themoment.gsmNetworking.domain.popup.domain.QPopup.popup
import team.themoment.gsmNetworking.domain.board.domain.QBoard.board
import team.themoment.gsmNetworking.domain.popup.dto.PopupListDto
import java.time.LocalDateTime

class PopupCustomRepositoryImpl (
    private val queryFactory: JPAQueryFactory
) : PopupCustomRepository {
    override fun findByCurrentPopup(): List<PopupListDto> {
        val currentDate = LocalDateTime.now()

        return queryFactory.select(
            Projections.constructor(
                PopupListDto::class.java,
                popup.id,
                popup.board.id,
                popup.board.title
            )
        ).from(popup)
            .innerJoin(popup.board, board)
            .where(popup.expTime.gt(currentDate))
            .fetch()
    }
}
