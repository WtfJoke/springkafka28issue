package com.example.kafkademoissue.messaging

import com.example.kafkademoissue.data.Movie
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
import java.time.Duration
import java.util.concurrent.TimeUnit


@ExtendWith(StartKafkaContainerBeforeAllExtension::class)
@SpringBootTest
class MovieIntegrationTest {

    @Autowired
    private lateinit var producer: KafkaTemplate<String, Movie>

    @Autowired
    private lateinit var kafkaProperties: KafkaProperties

    @Value("\${movie.topic}")
    private lateinit var topic: String

    @Test
    fun givenKafkaDockerContainer_whenSendingtoSimpleProducer_thenMessageIsSent() {
        val send = producer.send(topic, Movie("hiho"))
//        val consumer = createKafkaConsumer()

        await().atMost(5, TimeUnit.SECONDS).until { send.isDone };
        val result = send.get()
        assertThat(result).isNotNull()
        assertThat(result.producerRecord.value().title).isEqualTo("hiho")

//        val poll = consumer.poll(Duration.ofSeconds(20))
//        assertThat(poll.count()).isEqualTo(1)
//        val records = poll.records(topic)
//        val record = records.first()
//        assertThat(record.value().title).isEqualTo("hiho")
    }

    fun createKafkaConsumer(): KafkaConsumer<String, Movie> {
        val kafkaConsumer = KafkaConsumer<String, Movie>(kafkaProperties.buildConsumerProperties());
        kafkaConsumer.subscribe(listOf(topic))
        return kafkaConsumer;
    }
}