package team.themoment.gsmNetworking.thirdParty.aws.s3.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("spring.cloud.aws.s3")
class S3Properties(
    val bucketName: String,
    val existingImageBucketDomain: String,
    val imageBucketDomain: String
)
