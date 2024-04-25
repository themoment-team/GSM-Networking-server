package team.themoment.gsmNetworking.domain.popup.service.impl

import org.springframework.stereotype.Service
import team.themoment.gsmNetworking.domain.popup.dto.PopupListDto
import team.themoment.gsmNetworking.domain.popup.repository.PopupRepository
import team.themoment.gsmNetworking.domain.popup.service.QueryCurrentPopupUasCase

@Service
class QueryCurrentPopupService (
    private val popupRepository: PopupRepository
) : QueryCurrentPopupUasCase {
    override fun queryPopup(): List<PopupListDto> {
        return popupRepository.findByCurrentPopup();
    }
}
