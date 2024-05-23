package team.themoment.gsmNetworking.domain.board.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import team.themoment.gsmNetworking.domain.board.domain.Board

interface BoardRepository : JpaRepository<Board, Long>, BoardCustomRepository {

    @Query("select board from Board board where board.isPinned = true")
    fun findPinnedBoards(): List<Board>
}
