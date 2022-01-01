package com.example.kafkademoissue.messaging

import com.example.kafkademoissue.testcontainers.StartKafkaContainerBeforeAllExtension
import org.junit.jupiter.api.Test

class ConfluentContainerStartupTest {

    @Test
    fun containerStarted(){
        StartKafkaContainerBeforeAllExtension.kafkaContainer.start()
        StartKafkaContainerBeforeAllExtension.schemaRegistryContainer.start()
    }
}