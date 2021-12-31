package com.example.kafkademoissue.testcontainers

import com.github.dockerjava.api.command.InspectContainerResponse
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.images.builder.Transferable


class RedpandaContainer : GenericContainer<RedpandaContainer?>("vectorized/redpanda:v21.11.2") {
    init {
        withExposedPorts(9092, 8081, 8084, 29092)
        withCreateContainerCmdModifier { cmd -> cmd.withEntrypoint("sh") }
        withCommand("-c", "while [ ! -f " + STARTER_SCRIPT + " ]; do sleep 0.1; done; " + STARTER_SCRIPT)
        waitingFor(Wait.forLogMessage(".*Successfully started Redpanda!.*", 1))
    }


    override fun containerIsStarting(containerInfo: InspectContainerResponse?) {
        super.containerIsStarting(containerInfo)
        var command = "#!/bin/bash\n"
        command += "/usr/bin/rpk redpanda start --overprovisioned --smp 1 --reserve-memory 0M --node-id 0 "
        command += "--kafka-addr PLAINTEXT://0.0.0.0:29092,OUTSIDE://0.0.0.0:9092 "
        command += "--pandaproxy-addr 0.0.0.0:8084 "
        command += "--advertise-kafka-addr PLAINTEXT://" + host.toString() + ":29092,OUTSIDE://" + host.toString() + ":" + getMappedPort(9092)
        copyFileToContainer(
            Transferable.of(command.toByteArray(), 511),
            STARTER_SCRIPT
        )
    }

    companion object {
        private const val STARTER_SCRIPT = "/testcontainers_start.sh"
    }

    fun getSchemaRegistryUrl(): String {
        return "http://${this.host}:${this.getMappedPort(8081)}"
    }

    fun getBootstrapServers(): String? {
        return String.format("PLAINTEXT://%s:%s", host, getMappedPort(9092))
    }
}