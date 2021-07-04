package io.github.hebertrfreitas.localstack.infrastructure

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.core.sync.ResponseTransformer
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.*
import java.io.InputStream


@Service
class S3Storage(private val s3Client: S3Client) {

    val logger: Logger = LoggerFactory.getLogger(javaClass)

    init {
        println("S3client : $s3Client")
    }

    fun putObject(bucketName: String, key: String, data: ByteArray) {
        s3Client.putObject(PutObjectRequest.builder().bucket(bucketName).key(key).build(), RequestBody.fromBytes(data))
    }

    fun createBucket(bucketName: String) {
        val createBucketResponse = s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build())
        logger.info("createBucket response = location: ${createBucketResponse.location()} ")
    }

    fun listBuckets(): List<String> =
        s3Client.listBuckets().buckets().map { it.name() }

    fun listObjectsKeys(bucketName: String): List<String> {

        val listObjects = s3Client.listObjects(ListObjectsRequest.builder().bucket(bucketName).build())
        return listObjects.contents()
            .map { it.key() }
    }

    fun getObject(bucketName: String, key: String): InputStream {

        return s3Client.getObject(
            GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key).build(), ResponseTransformer.toInputStream()
        )

    }

    fun deleteObject(bucketName: String, key: String) {
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(key).build())
    }

    fun deleteBucket(bucketName: String) {
        s3Client.deleteBucket(DeleteBucketRequest.builder().bucket(bucketName).build())
    }

    fun deleteObjects(bucketName: String, keys: List<String>) {
        keys.forEach { key -> deleteObject(bucketName, key) }
    }

    fun deleteAllObjects(bucketName: String){
        this.listObjectsKeys(bucketName).forEach { deleteObject(bucketName,it) }
    }

}