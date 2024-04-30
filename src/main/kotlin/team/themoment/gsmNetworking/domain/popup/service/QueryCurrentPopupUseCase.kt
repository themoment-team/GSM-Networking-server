package team.themoment.gsmNetworking.domain.popup.service

import team.themoment.gsmNetworking.domain.popup.dto.PopupListDto

interface QueryCurrentPopupUseCase {
    fun queryPopup(): List<PopupListDto>
}
