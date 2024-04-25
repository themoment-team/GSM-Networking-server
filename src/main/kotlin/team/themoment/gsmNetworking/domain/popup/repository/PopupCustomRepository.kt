package team.themoment.gsmNetworking.domain.popup.repository

import team.themoment.gsmNetworking.domain.popup.dto.PopupListDto

interface PopupCustomRepository {
    fun findByCurrentPopup(): List<PopupListDto>
}
