package team.themoment.gsmNetworking.domain.board.domain

import team.themoment.gsmNetworking.common.domain.BaseIdTimestampEntity
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "file")
class File (
    val fileUrl: String,

    val fileName: String,

    @ManyToOne
    @JoinColumn(name = "board_id")
    val board: Board,
): BaseIdTimestampEntity()
