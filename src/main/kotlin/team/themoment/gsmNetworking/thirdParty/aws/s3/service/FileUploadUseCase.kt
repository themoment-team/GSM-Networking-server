package team.themoment.gsmNetworking.thirdParty.aws.s3.service

import org.springframework.web.multipart.MultipartFile
import team.themoment.gsmNetworking.domain.board.domain.Board
import team.themoment.gsmNetworking.domain.board.domain.File

interface FileUploadUseCase {

    fun fileUpload(multipartFile: List<MultipartFile>, board: Board): List<File>
}
