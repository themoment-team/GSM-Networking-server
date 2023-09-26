package team.themoment.gsmNetworking.domain.auth.domain

/**
 * 사용자의 권한을 판별하는 enum 클래스 입니다.
 */
enum class Authority {

    UNAUTHENTICATED,
    TEMP_USER,
    USER,
    ADMIN
    ;

}