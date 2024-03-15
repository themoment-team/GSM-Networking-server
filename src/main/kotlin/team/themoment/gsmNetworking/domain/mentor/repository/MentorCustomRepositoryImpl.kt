package team.themoment.gsmNetworking.domain.mentor.repository

import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.group.GroupBy.list
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.hibernate.NonUniqueResultException
import org.springframework.dao.IncorrectResultSizeDataAccessException
import team.themoment.gsmNetworking.domain.mentor.domain.QCareer.career
import team.themoment.gsmNetworking.domain.mentor.domain.QMentor.mentor
import team.themoment.gsmNetworking.domain.mentor.dto.CompanyInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.MentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.MyCareerInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.MyMentorInfoDto
import team.themoment.gsmNetworking.domain.user.domain.QUser.user

/**
 * mentor entity를 queryDsl로 사용하기 위한 custom repository 입니다.
 */
class MentorCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
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
                mentor.id,
                mentor.user.name,
                mentor.user.email,
                mentor.user.generation,
                career.position,
                Projections.constructor(
                    CompanyInfoDto::class.java,
                    career.companyName,
                    career.companyUrl
                ),
                mentor.user.snsUrl,
                mentor.user.profileUrl,
                mentor.registered
            )
        )
            .from(mentor, career)
            .join(mentor.user, user)
            .where(mentor.id.eq(career.mentor.id))
            .fetch()

    /**
     * 멘토를 조인하여 dto로 감싸 리턴 해주는 메서드입니다.
     *
     * @param authenticationId 회원의 식별자
     * @return 멘토의 정보를 감싼 dto
     */
    override fun findMyMentorInfoDto(authenticationId: Long): MyMentorInfoDto? {
        val myMentorInfoDto = queryFactory
            .selectFrom(mentor)
            .join(mentor.user)
            .leftJoin(career)
            .on(mentor.id.eq(career.mentor.id))
            .where(mentor.user.authenticationId.eq(authenticationId))
            .transform(
                groupBy(career.mentor.id).list(
                    Projections.constructor(
                        MyMentorInfoDto::class.java,
                        mentor.id,
                        mentor.user.name,
                        mentor.user.email,
                        mentor.user.phoneNumber,
                        mentor.user.generation,
                        mentor.user.snsUrl,
                        mentor.user.profileUrl,
                        mentor.registered,
                        list(
                            Projections.constructor(
                                MyCareerInfoDto::class.java,
                                career.id,
                                career.position,
                                career.companyName,
                                career.companyUrl,
                                career.startDate,
                                career.endDate,
                                career.isWorking
                            ),
                        )
                    )
                )
            )
        return if (myMentorInfoDto.isEmpty())
            null
        else if (myMentorInfoDto.size > 1)
            throw IncorrectResultSizeDataAccessException(myMentorInfoDto.size)
        else
            myMentorInfoDto[0]
    }
}
