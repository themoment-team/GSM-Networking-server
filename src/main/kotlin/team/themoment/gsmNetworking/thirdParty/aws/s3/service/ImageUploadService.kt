package team.themoment.gsmNetworking.thirdParty.aws.s3.service

import team.themoment.gsmNetworking.common.exception.ExpectedException
import io.awspring.cloud.s3.ObjectMetadata
import io.awspring.cloud.s3.S3Exception
import io.awspring.cloud.s3.S3Template
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import team.themoment.gsmNetworking.thirdParty.aws.s3.properties.S3Properties
import java.time.LocalDateTime
import java.util.*

@Service
class ImageUploadService(
    private val s3Properties: S3Properties,
    private val s3Template: S3Template
) {

    fun execute(multipartFile: MultipartFile): String =
        try {
            val originFileName = multipartFile.originalFilename
            val fileExtension = StringUtils.getFilenameExtension(originFileName)
                ?: throw ExpectedException("파일 확장자가 존재 하지 않는 파일입니다.", HttpStatus.BAD_REQUEST)
            val fileName = createFileName(fileExtension)
            s3Template.upload(s3Properties.bucketName, fileName, multipartFile.inputStream, ObjectMetadata.builder().contentType(validateFileExtension(fileExtension)).build())
            addS3ImageBucketDomain(fileName)
        } catch (e: S3Exception) {
            throw ExpectedException("AWS S3에서 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR)
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

    private fun addS3ImageBucketDomain(fileName: String): String{
        return s3Properties.imageBucketDomain + fileName
    }

}
