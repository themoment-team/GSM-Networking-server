package team.themoment.gsmNetworking.domain.feed.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.feed.dto.FeedInfoDto
import team.themoment.gsmNetworking.domain.feed.dto.FeedSaveDto
import team.themoment.gsmNetworking.domain.feed.service.GenerateFeedUseCase
import team.themoment.gsmNetworking.domain.feed.service.QueryFeedListUseCase

@RestController
@RequestMapping("/api/v1/feed")
class FeedController(
    private val generateFeedUseCase: GenerateFeedUseCase,
    private val queryFeedListUseCase: QueryFeedListUseCase
) {

    @PostMapping
    fun generateFeed(@RequestBody feedSaveDto: FeedSaveDto): ResponseEntity<Void> {
        generateFeedUseCase.generateFeed(feedSaveDto)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping
    fun queryFeedList(@RequestParam cursorId: Long,
                      @RequestParam pageSize: Long) : ResponseEntity<List<FeedInfoDto>> {

        if (cursorId < 0L || pageSize < 0L)
            throw ExpectedException("0이상부터 가능합니다.", HttpStatus.BAD_REQUEST)
        if (pageSize > 20L)
            throw ExpectedException("페이지 크기는 20이하까지 가능합니다.", HttpStatus.BAD_REQUEST)

        return ResponseEntity.ok(queryFeedListUseCase.queryFeedList(cursorId, pageSize))
    }

}
