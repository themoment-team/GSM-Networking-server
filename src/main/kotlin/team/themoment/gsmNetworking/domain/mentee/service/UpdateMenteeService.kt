package team.themoment.gsmNetworking.domain.mentee.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.auth.domain.Authority

/**
 * 멘티의 정보를 수정하는 로직이 담긴 클래스 입니다.
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class UpdateMenteeService(
    private val authenticatedUserManager: AuthenticatedUserManager
) {

    /**
     * 멘티의 권한을 수정한다.
     * 현재 단계에서는 멘티의 정보를 저장하고 있지 않기 때문에, 추후 인증 절차만 건너뛰도록 권한만 수정한다.
     */
    fun execute() {
        authenticatedUserManager.updateAuthority(Authority.TEMP_USER)
    }

}
