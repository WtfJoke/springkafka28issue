package com.example.kafkademoissue.testcontainers

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.Network
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
class StartKafkaContainerBeforeAllExtension : BeforeAllCallback {
    companion object {
        private const val CONFLUENT_VERSION = "7.0.1"

        @Container
        val kafkaContainer: KafkaContainer = KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:$CONFLUENT_VERSION")
        ).withNetwork(Network.newNetwork())

        @Container
        val schemaRegistryContainer: SchemaRegistryContainer = SchemaRegistryContainer(CONFLUENT_VERSION).withKafka(
            kafkaContainer
        )
    }

    override fun beforeAll(context: ExtensionContext?) {
        kafkaContainer.start()
        schemaRegistryContainer.start()

        System.setProperty("spring.kafka.bootstrap-servers", kafkaContainer.bootstrapServers)
        System.setProperty("spring.kafka.properties.schema.registry.url", schemaRegistryContainer.getSchemaRegistryUrl())
    }
}