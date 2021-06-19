package io.github.hebertrfreitas.localstack

import io.github.hebertrfreitas.localstack.infrastructure.S3FileStorage
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

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
    fun `Test - DeleteObject`(){
        assertDoesNotThrow { s3FileStorage.deleteBucket(BUCKET_NAME) }
        assert(s3FileStorage.listBuckets().isEmpty())
    }


}