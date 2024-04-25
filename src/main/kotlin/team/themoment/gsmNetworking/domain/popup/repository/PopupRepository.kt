package team.themoment.gsmNetworking.domain.popup.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.themoment.gsmNetworking.domain.popup.domain.Popup

interface PopupRepository : JpaRepository<Popup, Long>, PopupCustomRepository
