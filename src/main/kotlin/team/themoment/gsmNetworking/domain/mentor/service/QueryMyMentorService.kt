package team.themoment.gsmNetworking.domain.mentor.service

import team.themoment.gsmNetworking.domain.mentor.dto.MyMentorInfoDto

/**
 * 자신의 멘토 정보를 가져오는 인터페이스입니다.
 */
interface QueryMyMentorService {

    /**
     * 자신의 멘토 정보를 가져오는 메서드입니다..
     *
     * @param authenticationId 회원의 식별자
     * @return 자신의 멘토 정보를 담은 dto
     */
    fun execute(authenticationId:Long): MyMentorInfoDto
}
