package team.themoment.gsmNetworking.domain.popup.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.domain.popup.dto.PopupListDto
import team.themoment.gsmNetworking.domain.popup.service.QueryCurrentPopupUseCase

@RestController
@RequestMapping("/api/v1/popup")
class PopupController (
    private val popupUseCase: QueryCurrentPopupUseCase
) {
    @GetMapping
    fun queryPopup(): ResponseEntity<List<PopupListDto>> {
        return ResponseEntity.ok(popupUseCase.queryPopup())
    }
}
