package team.themoment.gsmNetworking.thirdParty.aws.s3.service

import org.springframework.web.multipart.MultipartFile

interface FileUploadUseCase {

    fun fileUpload(multipartFile: List<MultipartFile>): List<String>
}
