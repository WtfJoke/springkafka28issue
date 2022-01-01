package com.example.kafkademoissue.testcontainers

import com.github.dockerjava.api.command.InspectContainerResponse
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.images.builder.Transferable


class RedpandaContainer(version: String) : GenericContainer<RedpandaContainer>("vectorized/redpanda:${version}") {
    init {
        withExposedPorts(9092, 8081)
        withCreateContainerCmdModifier { cmd -> cmd.withEntrypoint("sh") }
        withCommand("-c", "while [ ! -f $STARTER_SCRIPT ]; do sleep 0.1; done; $STARTER_SCRIPT")
        waitingFor(Wait.forLogMessage(".*Successfully started Redpanda!.*", 1))
    }

    companion object {
        private const val STARTER_SCRIPT = "/startRedpanda.sh"
    }


    override fun containerIsStarting(containerInfo: InspectContainerResponse?) {
        super.containerIsStarting(containerInfo)
        val command = """
            #!/bin/bash
            /usr/bin/rpk redpanda start --overprovisioned --smp 1 --reserve-memory 0M --node-id 0 --check=false \
            --kafka-addr PLAINTEXT://0.0.0.0:29092,OUTSIDE://0.0.0.0:9092 \
            --pandaproxy-addr 0.0.0.0:8084 \
            --advertise-kafka-addr PLAINTEXT://${host}:29092,OUTSIDE://${host}:${getMappedPort(9092)}
        """.trimIndent()
        copyFileToContainer(
            Transferable.of(command.toByteArray(), 511),
            STARTER_SCRIPT
        )
    }

    fun getSchemaRegistryUrl(): String {
        return "http://${host}:${getMappedPort(8081)}"
    }

    fun getBootstrapServers(): String {
        return "PLAINTEXT://${host}:${getMappedPort(9092)}"
    }
}