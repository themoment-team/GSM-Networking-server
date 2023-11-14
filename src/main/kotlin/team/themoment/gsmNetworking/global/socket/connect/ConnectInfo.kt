package team.themoment.gsmNetworking.global.socket.connect

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

/**
 * 특정 WebSocket에 접속 중인 Client 정보를 저장하는 RedisHash 클래스입니다.
 */
// TODO 이거 어느 패키지에 있어야 하는게 맞을까?
//  개인적으로 인증 부분이 메인 패키지에 있는 걸 안 좋아하긴 한데, 의논해서 결정하기
@RedisHash(value = "connected_info")
data class ConnectInfo(
    @Id
    val sessionId: String,
    @Indexed
    val userId: Long,
    val subscribes: List<Subscribe> = emptyList()
) {
    data class Subscribe(
        val subscribeId: String,
        val subscribeUrl: String
    )

    /**
     * 새로운 [Subscribe]를 구독 목록에 추가합니다.
     *
     * @param subscribe 추가할 [Subscribe] 객체
     * @return 추가된 [Subscribe]를 포함한 새로운 [ConnectInfo] 인스턴스
     */
    fun addSubscribe(subscribe: Subscribe): ConnectInfo {
        val updatedSubscribes = mutableListOf(subscribe)
        updatedSubscribes.addAll(this.subscribes)
        return ConnectInfo(this.sessionId, this.userId, updatedSubscribes)
    }

    /**
     * 지정된 [subscribeId]를 가진 [Subscribe]를 구독 목록에서 제거합니다.
     *
     * @param subscribeId 제거할 [Subscribe]의 식별자
     * @return 지정된 [Subscribe]가 제거된 새로운 [ConnectInfo] 인스턴스,
     *  만약 [subscribeId]가 발견되지 않으면 원래의 [ConnectInfo] 인스턴스가 반환
     */
    fun removeSubscribe(subscribeId: String): ConnectInfo {
        val updatedSubscribes = this.subscribes.filter { it.subscribeId != subscribeId }
        return ConnectInfo(this.sessionId, this.userId, updatedSubscribes)
    }
}
