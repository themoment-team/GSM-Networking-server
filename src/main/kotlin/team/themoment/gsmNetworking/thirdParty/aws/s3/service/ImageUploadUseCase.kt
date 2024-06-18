package team.themoment.gsmNetworking.thirdParty.aws.s3.service

import org.springframework.web.multipart.MultipartFile

interface ImageUploadUseCase {

    fun imageUpload(multipartFile: MultipartFile): String
}
