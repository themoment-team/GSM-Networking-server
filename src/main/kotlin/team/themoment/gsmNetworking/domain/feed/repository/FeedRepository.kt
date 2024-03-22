package team.themoment.gsmNetworking.domain.feed.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.themoment.gsmNetworking.domain.feed.domain.Feed

interface FeedRepository: JpaRepository<Feed, Long>, FeedCustomRepository
