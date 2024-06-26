package team.themoment.gsmNetworking.domain.popup.domain

import team.themoment.gsmNetworking.common.domain.BaseIdTimestampEntity
import team.themoment.gsmNetworking.domain.board.domain.Board
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "popup")
class Popup (
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id",)
    val board: Board,

    @Column(name = "exp_time",)
    val expTime: LocalDateTime
): BaseIdTimestampEntity()
