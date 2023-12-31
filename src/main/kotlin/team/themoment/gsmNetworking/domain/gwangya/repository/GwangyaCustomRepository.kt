package team.themoment.gsmNetworking.domain.gwangya.repository

import team.themoment.gsmNetworking.domain.gwangya.dto.GwangyaPostsDto

interface GwangyaCustomRepository {

    fun findPagebyCursorId(cursorId: Long, pageSize: Long): List<GwangyaPostsDto>

    fun findPageWithRecentPosts(pageSize: Long): List<GwangyaPostsDto>
}
