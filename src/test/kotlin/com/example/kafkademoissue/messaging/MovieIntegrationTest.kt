package com.example.kafkademoissue.messaging

import com.example.kafkademoissue.data.Movie
import com.example.kafkademoissue.data.Show
import com.example.kafkademoissue.testcontainers.RedpandaContainer
import com.example.kafkademoissue.testcontainers.StartRedPandaBeforeAllExtension
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import org.testcontainers.shaded.org.awaitility.Awaitility.await
import java.util.concurrent.TimeUnit


@ExtendWith(StartRedPandaBeforeAllExtension::class)
@SpringBootTest
class MovieIntegrationTest {

    @Autowired
    private lateinit var producer: KafkaTemplate<String, Movie>

    @Autowired
    private lateinit var showProducer: KafkaTemplate<String, Show>

    @Autowired
    private lateinit var kafkaProperties: KafkaProperties

    @Value("\${movie.topic}")
    private lateinit var topic: String

    @Test
    fun givenKafkaDockerContainer_whenSendingtoSimpleProducer_thenMessageIsSent() {
        val send = producer.send(topic, Movie("hiho"))
        await().atMost(5, TimeUnit.SECONDS).until { send.isDone };
        val result = send.get()
        assertThat(result).isNotNull()
        assertThat(result.producerRecord.value().title).isEqualTo("hiho")
    }

    @Test
    fun givenKafkaDockerContainer_whenSendingtoSimpleShowProducer_thenMessageIsSent() {
        val send = showProducer.send(topic, Show("hiho"))

        await().atMost(5, TimeUnit.SECONDS).until { send.isDone };
        val result = send.get()
        assertThat(result).isNotNull()
        assertThat(result.producerRecord.value().title).isEqualTo("hiho")
    }

    @Test
    fun startRedpanda(){
        val container = RedpandaContainer()

        container.start()
    }

}