package team.themoment.gsmNetworking.domain.board.service

interface DeleteFileUseCase {

    fun deleteFile(authenticationId: Long, fileId: Long)
}
