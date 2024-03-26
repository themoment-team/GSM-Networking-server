package team.themoment.gsmNetworking.domain.board.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.themoment.gsmNetworking.domain.board.domain.Board

interface BoardRepository: JpaRepository<Board, Long>, BoardCustomRepository
