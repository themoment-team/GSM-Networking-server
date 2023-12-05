package team.themoment.gsmNetworking.domain.gwangya.repository

import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostDto

interface GwangyaCustomRepository {

    fun findPagebyCursorId(cursorId: Long, pageSize: Long): List<GwangyaPostDto>

    fun findPageWithRecentPosts(pageSize: Long): List<GwangyaPostDto>
}
