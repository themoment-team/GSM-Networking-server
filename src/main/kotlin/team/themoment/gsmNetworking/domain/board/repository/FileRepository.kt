package team.themoment.gsmNetworking.domain.board.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.themoment.gsmNetworking.domain.board.domain.Board
import team.themoment.gsmNetworking.domain.board.domain.File

interface FileRepository : JpaRepository<File, Long>{

    fun findFilesByBoard(board: Board): List<File>
}
