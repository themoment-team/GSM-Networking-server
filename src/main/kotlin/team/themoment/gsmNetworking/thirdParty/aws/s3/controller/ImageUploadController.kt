package team.themoment.gsmNetworking.thirdParty.aws.s3.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import team.themoment.gsmNetworking.thirdParty.aws.s3.service.ImageUploadUseCase

@RestController
@RequestMapping("api/v1/file")
class ImageUploadController(
    private val imageUploadUseCase: ImageUploadUseCase
) {

    @PostMapping
    fun uploadFile(@RequestParam("file", required = true) multipartFile: MultipartFile): ResponseEntity<Map<String, String>> =
        imageUploadUseCase.imageUpload(multipartFile)
            .let { ResponseEntity.ok(mapOf("fileUrl" to it)) }

}
