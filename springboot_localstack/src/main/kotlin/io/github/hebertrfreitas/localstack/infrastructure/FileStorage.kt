package io.github.hebertrfreitas.localstack.infrastructure

import java.io.File
import java.io.InputStream

interface FileStorage{

    fun createBucket(bucketName: String)
    fun listBuckets(): List<String>
    fun putObject(data : Array<Byte>)
    fun listObjects(bucketName: String) : List<InputStream>
    fun deleteObject(bucketName: String, key: String)
    fun deleteBucket(bucketName: String)
}