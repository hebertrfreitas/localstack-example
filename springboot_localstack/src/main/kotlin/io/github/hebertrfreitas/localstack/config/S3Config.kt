package io.github.hebertrfreitas.localstack.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import java.net.URI

@Configuration
class S3Config {


    @Bean
    fun s3Client(): S3Client{
        return S3Client.builder()
            .region(Region.US_EAST_1)
            .endpointOverride(URI("http://localhost:4566"))
            .build()
    }


}