package io.github.hebertrfreitas.localstack

import io.github.hebertrfreitas.localstack.infrastructure.S3Storage
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*



@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class S3Test {

    @Autowired
    lateinit var s3FileStorage: S3Storage

    fun deleteAll(){
        s3FileStorage.listBuckets().forEach { bucketName ->
            s3FileStorage.deleteObjects(bucketName, s3FileStorage.listObjectsKeys(bucketName))
            s3FileStorage.deleteBucket(bucketName)
        }
    }

    fun randomBucketName() = "test-bucket-${UUID.randomUUID()}"

    @BeforeAll
    @AfterAll
    fun init() {
        this.deleteAll()
    }



    @Test
    fun `Test - Bucket Operations`() {
        val bucketName = randomBucketName()
        println("create bucket - bucket name: $bucketName")
        assertDoesNotThrow { s3FileStorage.createBucket(bucketName) }
        assert(s3FileStorage.listBuckets().any { it == bucketName })
        assertDoesNotThrow { s3FileStorage.deleteBucket(bucketName) }
        assert(s3FileStorage.listBuckets().none{it == bucketName} )
    }

    @Test
    fun `Test - Object Operations`() {
        val key = "simple_file.txt"
        val bucketName = randomBucketName()
        println("object operations - bucket name: $bucketName")
        s3FileStorage.createBucket(bucketName)

        val byteArray = this.javaClass.getResourceAsStream("/static/$key").readAllBytes()
        s3FileStorage.putObject(bucketName, key, byteArray)
        assert(s3FileStorage.listObjectsKeys(bucketName).any { it == key })

        val element = s3FileStorage.getObject(bucketName, key)
        Assertions.assertNotNull(element.readAllBytes())

        assertDoesNotThrow { s3FileStorage.deleteObject(bucketName, key) }
        assert(s3FileStorage.listObjectsKeys(bucketName).none { it == key })
    }




}