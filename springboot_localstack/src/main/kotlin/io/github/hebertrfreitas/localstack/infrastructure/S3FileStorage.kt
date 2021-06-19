package io.github.hebertrfreitas.localstack.infrastructure

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import software.amazon.awssdk.core.sync.ResponseTransformer
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.*
import java.io.InputStream


@Service
class S3FileStorage(private val s3Client: S3Client): FileStorage {

    val logger: Logger = LoggerFactory.getLogger(javaClass)

    init {
        println("S3client : $s3Client")
    }

    override fun putObject(data: Array<Byte>) {
        TODO("Not yet implemented")
    }

    override fun createBucket(bucketName: String) {
        val createBucketResponse = s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build())
        logger.info("createBucket response = location: ${createBucketResponse.location()} ")
    }

    override fun listBuckets(): List<String> =
       s3Client.listBuckets().buckets().map { it.name() }

    override fun listObjects(bucketName: String): List<InputStream> {

        val listObjects = s3Client.listObjects(ListObjectsRequest.builder().bucket(bucketName).build())
        return listObjects.contents()
            .map { getObject(bucketName, it.key()) }
    }

    private fun getObject(bucketName: String, key : String): InputStream{

        return s3Client.getObject(
            GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key).build(), ResponseTransformer.toInputStream()
        )

    }

    override fun deleteObject(bucketName: String, key: String){
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(key).build())
    }

    override fun deleteBucket(bucketName: String){
        s3Client.deleteBucket(DeleteBucketRequest.builder().bucket(bucketName).build())
    }
}