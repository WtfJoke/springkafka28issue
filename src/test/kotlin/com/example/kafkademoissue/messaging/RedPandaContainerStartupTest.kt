package com.example.kafkademoissue.messaging

import com.example.kafkademoissue.testcontainers.StartRedPandaBeforeAllExtension
import org.junit.jupiter.api.Test

class RedPandaContainerStartupTest {

    @Test
    fun containerStarted(){
        StartRedPandaBeforeAllExtension.kafkaContainer.start()
    }
}