package team.themoment.gsmNetworking.domain.mentor.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import team.themoment.gsmNetworking.domain.mentor.dto.MentorCompanyAddressListDto
import team.themoment.gsmNetworking.domain.mentor.domain.QCareer.career
import team.themoment.gsmNetworking.domain.user.domain.QUser.user
import team.themoment.gsmNetworking.domain.mentor.domain.QMentor.mentor
import team.themoment.gsmNetworking.domain.mentor.dto.CompanyAddressDto

class CareerCustomRepositoryImpl (
    private val queryFactory: JPAQueryFactory
) : CareerCustomRepository {
    override fun queryAllCompanyAddress(): List<MentorCompanyAddressListDto> {
        return queryFactory.select(
            Projections.constructor(
                MentorCompanyAddressListDto::class.java,
                mentor.id,
                mentor.user.name,
                mentor.user.generation,
                Projections.constructor(
                    CompanyAddressDto::class.java,
                    career.companyName,
                    career.position,
                    career.lat,
                    career.lon
                )
            )
        )
            .from(career)
            .innerJoin(career.mentor, mentor)
            .on(career.mentor.eq(mentor))
            .innerJoin(mentor.user, user)
            .on(mentor.user.eq(user))
            .where(career.isWorking.isTrue)
            .fetch()
    }
}