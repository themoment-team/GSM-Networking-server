package team.themoment.gsmNetworking.domain.like.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.themoment.gsmNetworking.domain.like.domain.Like

interface LikeRepository: JpaRepository<Like, Long> {
}