package team.themoment.gsmNetworking.domain.like.service.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.board.repository.BoardRepository
import team.themoment.gsmNetworking.domain.like.domain.Like
import team.themoment.gsmNetworking.domain.like.dto.LikeStatusDto
import team.themoment.gsmNetworking.domain.like.repository.LikeRepository
import team.themoment.gsmNetworking.domain.like.service.ToggleLikeUseCase
import team.themoment.gsmNetworking.domain.user.repository.UserRepository

@Service
class ToggleLikeUseCaseImpl (
    private val likeRepository: LikeRepository,
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository
) : ToggleLikeUseCase {

    @Transactional
    override fun likeToggle(boardId: Long, authenticationId: Long): LikeStatusDto {
        val currentUser = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        val board = boardRepository.findById(boardId)
            .orElseThrow { ExpectedException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND) }

        val like = likeRepository.findByUserAndBoard(
            user = currentUser,
            board = board
        )

        if (like != null) {
            likeRepository.delete(like)
        } else {
            likeRepository.save(
                Like(user = currentUser, board = board)
            )
        }

        return LikeStatusDto(like != null)
    }
}
