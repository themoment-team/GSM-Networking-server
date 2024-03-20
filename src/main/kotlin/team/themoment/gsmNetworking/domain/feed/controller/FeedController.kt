package team.themoment.gsmNetworking.domain.feed.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.domain.feed.dto.FeedSaveDto
import team.themoment.gsmNetworking.domain.feed.service.GenerateFeedUseCase

@RestController
@RequestMapping("/api/v1/feed")
class FeedController(
    private val generateFeedUseCase: GenerateFeedUseCase
) {

    @PostMapping
    fun generateFeed(@RequestBody feedSaveDto: FeedSaveDto): ResponseEntity<Void> {
        generateFeedUseCase.generateFeed(feedSaveDto)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }


}
