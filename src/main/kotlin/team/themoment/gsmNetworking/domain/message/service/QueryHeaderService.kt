package team.themoment.gsmNetworking.domain.message.service

import team.themoment.gsmNetworking.domain.message.dto.domain.HeaderDto
import team.themoment.gsmNetworking.domain.message.dto.api.res.HeaderRes
import team.themoment.gsmNetworking.domain.message.dto.api.res.MessageRes
import team.themoment.gsmNetworking.domain.message.enums.QueryDirection
import java.time.Instant

/**
 * 두 사용자 사이의 채팅 메타데이터를 반환하는 역할의 Service.
 */
interface QueryHeaderService {

    /**
     * 두 사용자 사이의 채팅 메타데이터를 반환합니다.
     *
     * @param user1Id 사용자1의 고유 식별자
     * @param user2Id 사용자2의 고유 식별자
     * @return nullable [HeaderDto], 두 사용자간의 채팅 기록이 없는 경우 null 반환
     */
    fun getHeaderByUserIds(user1Id: Long, user2Id: Long): HeaderDto?

}
