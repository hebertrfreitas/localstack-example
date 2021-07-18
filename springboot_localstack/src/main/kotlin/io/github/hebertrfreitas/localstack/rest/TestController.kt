package io.github.hebertrfreitas.localstack.rest

import io.github.hebertrfreitas.localstack.infrastructure.S3Storage
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController(val s3Service: S3Storage) {

    @GetMapping("/test")
    fun test():ResponseEntity<Any> {
        s3Service.createBucket("bucket-rest")
        return ResponseEntity.ok().build()
    }

}