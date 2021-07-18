package io.github.hebertrfreitas.localstack.infrastructure

import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest
import software.amazon.awssdk.services.sqs.model.Message
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest

@Service
class SqsService(private val sqsClient: SqsClient) {


    fun getQueueUrl(queueName: String): String =
        sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build()).queueUrl()
    

    fun receiveMessage(queueName: String): List<Message> {

        return sqsClient.receiveMessage(
            ReceiveMessageRequest.builder().maxNumberOfMessages(10)
                .queueUrl(getQueueUrl(queueName)).build()
        )
            .messages()
    }

}