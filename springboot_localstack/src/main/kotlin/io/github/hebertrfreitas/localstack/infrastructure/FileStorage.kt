package io.github.hebertrfreitas.localstack.infrastructure

import java.io.File
import java.io.InputStream

interface FileStorage{

    fun createBucket(bucketName: String)
    fun listBuckets(): List<String>
    fun putObject(bucketName:String, key:String, data : ByteArray)
    fun listObjectsKeys(bucketName: String) : List<String>
    fun deleteObject(bucketName: String, key: String)
    fun deleteBucket(bucketName: String)
}