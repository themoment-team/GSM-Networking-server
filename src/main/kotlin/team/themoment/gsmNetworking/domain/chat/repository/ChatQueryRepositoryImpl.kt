package team.themoment.gsmNetworking.domain.chat.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import team.themoment.gsmNetworking.domain.chat.domain.QBaseChat.baseChat
import team.themoment.gsmNetworking.domain.chat.domain.QSystemChat.systemChat
import team.themoment.gsmNetworking.domain.chat.domain.QUserChat.userChat

class ChatQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ChatQueryRepository {

}
