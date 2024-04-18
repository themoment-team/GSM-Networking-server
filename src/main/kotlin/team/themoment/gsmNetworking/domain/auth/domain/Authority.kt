package team.themoment.gsmNetworking.domain.auth.domain

/**
 * 사용자의 권한을 판별하는 enum 클래스 입니다.
 */
enum class Authority(
    val role: String
) {

    UNAUTHENTICATED("ROLE_UNAUTHENTICATED"),
    TEMP_USER("ROLE_TEMP_USER"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    TEACHER("ROLE_TEACHER")
    ;

}
