package team.themoment.gsmNetworking.domain.gwangya.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostDto
import team.themoment.gsmNetworking.domain.gwangya.repository.GwangyaCustomRepository
import java.time.ZoneId

@Service
@Transactional(readOnly = true)
class QueryGwangyaPostService(
    private val gwangyaRepository: GwangyaCustomRepository
) {

    fun execute(cursorId: Long, pageSize: Long): List<GwangyaPostDto> =
        if (cursorId == 0L)
            convertPostsCreatedAtToKoreanTime(
                gwangyaRepository.findPageWithRecentPosts(pageSize)
            )
        else
            convertPostsCreatedAtToKoreanTime(
                gwangyaRepository.findPagebyCursorId(cursorId, pageSize)
            )

    private fun convertPostsCreatedAtToKoreanTime(gwangyaPostDtos: List<GwangyaPostDto>): List<GwangyaPostDto> =
        gwangyaPostDtos
            .map {
                GwangyaPostDto(
                    it.id,
                    it.content,
                    it.createdAt.atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime()
                )
            }
}
