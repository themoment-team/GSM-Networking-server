package team.themoment.gsmNetworking.domain.message.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import team.themoment.gsmNetworking.common.util.UUIDUtils
import team.themoment.gsmNetworking.domain.message.domain.Header
import team.themoment.gsmNetworking.domain.message.domain.QHeader.header
import team.themoment.gsmNetworking.domain.message.domain.QMessage.message
import team.themoment.gsmNetworking.domain.message.domain.QUserMessageInfo.userMessageInfo
import team.themoment.gsmNetworking.domain.message.dto.domain.MessageMetaDto
import team.themoment.gsmNetworking.domain.message.dto.domain.MessageDto
import team.themoment.gsmNetworking.domain.message.enums.QueryDirection
import java.time.Instant


class MessageCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : MessageCustomRepository {
    override fun findMessageInfosByUserId(userId: Long, time: Instant, limit: Long): List<MessageMetaDto> =
        queryFactory
            .select(
                Projections.constructor(
                    MessageMetaDto::class.java,
                    header.user1Id,
                    header.user2Id,
                    message.direction,
                    header.recentMessageId,
                    userMessageInfo.lastViewedEpochMilli
                )
            )
            .from(header)
            .innerJoin(message)
            .on(
                header.user1Id.eq(userId).or(header.user2Id.eq(userId)),
                recentChatIdLt(time),
                message.messageId.eq(header.recentMessageId),
            )
            .innerJoin(userMessageInfo)
            .on(
                userMessageInfo.userId.eq(userId),
                (userMessageInfo.opponentUserId.`in`(header.user1Id, header.user2Id))
            )
            .orderBy(header.recentMessageId.desc())
            .limit(limit)
            .fetch()

    override fun findMessagesBetweenUsers(
        user1Id: Long,
        user2Id: Long,
        time: Instant,
        limit: Long,
        direction: QueryDirection
    ): List<MessageDto> {
        val timeCondition = when (direction) {
            QueryDirection.NEXT -> recentChatIdLt(time)
            QueryDirection.PREVIOUS -> message.messageId.goe(UUIDUtils.generateSmallestUUIDv7(time))
        }
        return queryFactory
            .select(
                Projections.constructor(
                    MessageDto::class.java,
                    message.messageId,
                    message.user1Id,
                    message.user2Id,
                    message.direction,
                    message.content
                )
            )
            .from(message)
            .where(
                message.user1Id.eq(user1Id),
                message.user2Id.eq(user2Id),
                timeCondition
            )
            .orderBy(message.messageId.desc())
            .limit(limit)
            .fetch()
    }

    override fun findHeaderBetweenUsers(user1Id: Long, user2Id: Long): Header? =
        queryFactory
            .selectFrom(header)
            .where(
                header.user1Id.eq(user1Id),
                header.user2Id.eq(user2Id)
            )
            .fetchOne()

    private fun recentChatIdLt(time: Instant): BooleanExpression =
        // 입력받은 시간 + 1을 미만 조건으로 검색
        header.recentMessageId.lt(UUIDUtils.generateSmallestUUIDv7(time.plusMillis(1)))

}
