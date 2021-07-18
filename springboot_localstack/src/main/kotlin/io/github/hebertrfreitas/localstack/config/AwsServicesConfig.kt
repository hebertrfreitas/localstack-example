package io.github.hebertrfreitas.localstack.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.client.builder.SdkClientBuilder
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3ClientBuilder
import software.amazon.awssdk.services.sqs.SqsClient
import java.lang.Exception
import java.net.URI

@ConstructorBinding
@ConfigurationProperties(prefix = "aws")
class AwsProperties(
    val region: String?,
    val endpoint: String?
) {

    fun getAwsRegionEnum(): Region {
        return try {
            Region.of(region)
        } catch (e: Exception) {
            Region.US_EAST_1
        }
    }

}


@Configuration
class AwsServicesConfig(val awsProperties: AwsProperties) {


    @Bean
    fun s3Client(): S3Client {

        return S3Client.builder()
            .region(awsProperties.getAwsRegionEnum())
            .apply {
                if(awsProperties.endpoint != null) endpointOverride(URI(awsProperties.endpoint))
            }
            .build()
    }


    @Bean
    fun sqsClient(): SqsClient {

        return SqsClient.builder()
            .region(Region.US_EAST_1)
            .apply {
                if(awsProperties.endpoint != null) endpointOverride(URI(awsProperties.endpoint))
            }
            .build()

    }


}