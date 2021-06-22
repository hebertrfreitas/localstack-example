package io.github.hebertrfreitas.localstack

import io.github.hebertrfreitas.localstack.infrastructure.S3FileStorage
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.ByteArrayOutputStream
import java.io.File

const val BUCKET_NAME = "test-bucket"

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class S3Test {

    @Autowired
    lateinit var s3FileStorage : S3FileStorage

    @BeforeAll
    fun init(){
        s3FileStorage.listBuckets().forEach { bucketName -> s3FileStorage.deleteBucket(bucketName) }
    }

    @Test
    fun `Test - CreateBucket`(){
        assertDoesNotThrow { s3FileStorage.createBucket(BUCKET_NAME) }
        assert(s3FileStorage.listBuckets().any { it == BUCKET_NAME })
    }

    @Test
    fun `Test - DeleteBucket`(){
        assertDoesNotThrow { s3FileStorage.deleteBucket(BUCKET_NAME) }
        assert(s3FileStorage.listBuckets().isEmpty())
    }

    @Test
    fun `Test - Object Operations`(){
        val key = "simple_file.txt"

        s3FileStorage.createBucket(BUCKET_NAME)

        val byteArray = this.javaClass.getResourceAsStream("/static/$key").readAllBytes()
        s3FileStorage.putObject(BUCKET_NAME, key, byteArray)
        assert(s3FileStorage.listObjectsKeys(BUCKET_NAME).any { it == key} )

        assertDoesNotThrow { s3FileStorage.deleteObject(BUCKET_NAME, key) }
        assert(s3FileStorage.listObjectsKeys(BUCKET_NAME).none { it == key })
    }






}