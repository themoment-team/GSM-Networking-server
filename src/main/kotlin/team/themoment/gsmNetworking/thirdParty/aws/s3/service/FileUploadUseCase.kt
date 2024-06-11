package team.themoment.gsmNetworking.thirdParty.aws.s3.service

import org.springframework.web.multipart.MultipartFile
import team.themoment.gsmNetworking.domain.board.dto.FileUrlsDto

interface FileUploadUseCase {

    fun fileUpload(multipartFile: List<MultipartFile>): FileUrlsDto
}
