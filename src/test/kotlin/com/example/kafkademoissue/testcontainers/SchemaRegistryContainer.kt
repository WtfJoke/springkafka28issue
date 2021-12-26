package com.example.kafkademoissue.testcontainers

import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.Network

class SchemaRegistryContainer(version: String) : GenericContainer<SchemaRegistryContainer?>("confluentinc/cp-schema-registry:$version") {

    init {
        withExposedPorts(8081)
    }

    fun withKafka(kafka: KafkaContainer): SchemaRegistryContainer {
        return withKafka(kafka.network, kafka.networkAliases[0].toString() + ":9092")
    }

    private fun withKafka(network: Network?, bootstrapServers: String): SchemaRegistryContainer {
        withNetwork(network!!)
        withEnv("SCHEMA_REGISTRY_HOST_NAME", "schema-registry")
        withEnv("SCHEMA_REGISTRY_LISTENERS", "http://0.0.0.0:8081")
        withEnv("SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS", "PLAINTEXT://$bootstrapServers")
        return this
    }

    fun getSchemaRegistryUrl(): String {
        return "http://${this.containerIpAddress}:${this.getMappedPort(8081)}"
    }
}