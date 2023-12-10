package team.themoment.gsmNetworking.domain.message.service

import team.themoment.gsmNetworking.domain.message.dto.api.res.HeaderRes
import java.time.Instant

/**
 * 한 사용자의 채팅 목록을 특정 시간을 기준으로 가져오는 역할의 Service.
 */
interface QueryHeadersService {

    /**
     * 한 사용자의 채팅 목록을 특정 시간을 기준으로 가져옵니다.
     *
     * @param userId 사용자의 고유 식별자
     * @param time 결과의 기준이 되는 시간
     * @param limit 결과 목록의 길이 제한
     * @return [List]<[HeaderRes]>
     */
    fun getMessageInfosByUserId(userId: Long, time: Instant, limit: Long): List<HeaderRes>

}
