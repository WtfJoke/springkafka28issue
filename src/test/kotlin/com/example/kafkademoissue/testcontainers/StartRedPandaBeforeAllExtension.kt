package com.example.kafkademoissue.testcontainers

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class StartRedPandaBeforeAllExtension : BeforeAllCallback {
    companion object {
        private const val REDPANDA_VERSION = "v21.11.2"

        @Container
        val kafkaContainer: RedpandaContainer = RedpandaContainer(REDPANDA_VERSION)
    }

    override fun beforeAll(context: ExtensionContext) {
        kafkaContainer.start()

        System.setProperty("spring.kafka.bootstrap-servers", kafkaContainer.getBootstrapServers())
        System.setProperty("spring.kafka.properties.schema.registry.url", kafkaContainer.getSchemaRegistryUrl())
    }
}