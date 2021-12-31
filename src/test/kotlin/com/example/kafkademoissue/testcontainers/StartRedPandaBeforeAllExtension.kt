package com.example.kafkademoissue.testcontainers

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.Network
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class StartRedPandaBeforeAllExtension : BeforeAllCallback {
    companion object {
        @Container
        val kafkaContainer: RedpandaContainer = RedpandaContainer().withNetwork(Network.newNetwork())!!
    }

    override fun beforeAll(context: ExtensionContext?) {
        kafkaContainer.start()

        val schemaRegistryUrl = kafkaContainer.getSchemaRegistryUrl()
        println(schemaRegistryUrl)

        System.setProperty("spring.kafka.bootstrap-servers", kafkaContainer.getBootstrapServers())
        System.setProperty("spring.kafka.properties.schema.registry.url", schemaRegistryUrl)
    }
}