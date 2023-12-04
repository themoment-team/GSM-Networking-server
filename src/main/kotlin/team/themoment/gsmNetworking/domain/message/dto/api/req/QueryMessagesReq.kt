package team.themoment.gsmNetworking.domain.message.dto.api.req

import team.themoment.gsmNetworking.domain.message.enums.QueryDirection

data class QueryMessagesReq(
    val user1Id: Long,
    val user2Id: Long,
    val epochMilli: Long,
    val limit: Long,
    val direction: QueryDirection
)
