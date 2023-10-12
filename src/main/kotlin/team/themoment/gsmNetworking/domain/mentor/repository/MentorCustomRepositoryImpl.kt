package team.themoment.gsmNetworking.domain.mentor.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import team.themoment.gsmNetworking.domain.mentor.domain.QCareer.career
import team.themoment.gsmNetworking.domain.mentor.domain.QMentor.mentor
import team.themoment.gsmNetworking.domain.mentor.dto.MentorInfoDto
import team.themoment.gsmNetworking.domain.user.domain.QUser.user

/**
 * mentor entity를 queryDsl로 사용하기 위한 custom repository 입니다.
 */
class MentorCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : MentorCustomRepository {

    /**
     * 멘토와 커리어를 조인하여 dto로 감싸 리턴 해주는 메서드 입니다.
     *
     * @return 멘토의 정보를 감싼 dto
     */
    override fun findAllMentorInfoDto(): List<MentorInfoDto> =
        queryFactory.select(
            Projections.constructor(
                MentorInfoDto::class.java,
                mentor.mentorId,
                mentor.user.name,
                mentor.user.email,
                mentor.user.generation,
                career.position,
                Projections.constructor(
                    MentorInfoDto.CompanyInfo::class.java,
                    career.companyName,
                    career.companyUrl
                ),
                mentor.user.snsUrl,
                mentor.user.profileUrl
            )
        )
            .from(mentor, career)
            .join(mentor.user, user)
            .where(mentor.mentorId.eq(career.mentor.mentorId))
            .fetch()

}
