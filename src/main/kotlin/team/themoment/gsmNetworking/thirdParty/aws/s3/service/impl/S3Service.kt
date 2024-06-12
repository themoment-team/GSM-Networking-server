package team.themoment.gsmNetworking.thirdParty.aws.s3.service.impl

import io.awspring.cloud.s3.ObjectMetadata
import io.awspring.cloud.s3.S3Exception
import io.awspring.cloud.s3.S3Template
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.board.dto.FileUrlsDto
import team.themoment.gsmNetworking.thirdParty.aws.s3.properties.S3Properties
import team.themoment.gsmNetworking.thirdParty.aws.s3.service.FileUploadUseCase
import team.themoment.gsmNetworking.thirdParty.aws.s3.service.ImageUploadUseCase
import java.time.LocalDateTime
import java.util.*

@Service
class S3Service(
    private val s3Template: S3Template,
    private val s3Properties: S3Properties,
) : ImageUploadUseCase,
    FileUploadUseCase {

    override fun imageUpload(multipartFile: MultipartFile): String {
        val originFileName = multipartFile.originalFilename
        val fileExtension = StringUtils.getFilenameExtension(originFileName)
            ?: throw ExpectedException("파일 확장자가 존재 하지 않는 파일입니다.", HttpStatus.BAD_REQUEST)
        return s3Upload(multipartFile, validateFileExtension(fileExtension))
    }

    override fun fileUpload(multipartFile: List<MultipartFile>): FileUrlsDto {
        try {
            val fileUrls = multipartFile
                .map {
                    s3Upload(it, it.contentType)
                }

            return FileUrlsDto(fileUrls)
        } catch (e: S3Exception) {
            throw ExpectedException("AWS S3에서 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    private fun s3Upload(multipartFile: MultipartFile, contentType: String?): String {
        try {
            val originFileName = multipartFile.originalFilename
            val fileExtension = StringUtils.getFilenameExtension(originFileName)
                ?: throw ExpectedException("파일 확장자가 존재 하지 않는 파일입니다.", HttpStatus.BAD_REQUEST)
            val fileName = createFileName(fileExtension)
            s3Template.upload(
                s3Properties.bucketName,
                fileName,
                multipartFile.inputStream,
                ObjectMetadata.builder().contentType(contentType).build()
            )
            return addS3BucketDomain(fileName)
        } catch (e: S3Exception) {
            throw ExpectedException("AWS S3에서 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    private fun createFileName(fileExtension: String): String {
        return UUID.randomUUID().toString() + LocalDateTime.now() + "." + fileExtension
    }

    private fun validateFileExtension(fileExtension: String): String {
        val allowExtension = listOf("jpg", "jpeg", "png")
        if (!allowExtension.contains(fileExtension.lowercase())) {
            throw ExpectedException("지원하지 않는 파일 확장자 입니다.", HttpStatus.BAD_REQUEST)
        }
        return fileExtension
    }

    private fun addS3BucketDomain(fileName: String): String {
        return s3Properties.bucketDomain + fileName
    }
}
