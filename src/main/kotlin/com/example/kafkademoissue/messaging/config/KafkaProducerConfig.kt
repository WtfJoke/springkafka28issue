package com.example.kafkademoissue.messaging.config

import com.example.kafkademoissue.data.Movie
import com.example.kafkademoissue.data.Show
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.JacksonUtils
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
class KafkaProducerConfig(val kafkaProperties: KafkaProperties) {
    @Bean
    fun movieProducerFactory(): ProducerFactory<String, Movie> {
        return DefaultKafkaProducerFactory(kafkaProperties.buildProducerProperties())
    }

    @Bean
    fun movieKafkaTemplate(): KafkaTemplate<String, Movie> {
        return KafkaTemplate(movieProducerFactory())
    }

    @Bean
    fun showProducerFactory(): ProducerFactory<String, Show> {
        return DefaultKafkaProducerFactory(
            kafkaProperties.buildProducerProperties(),
            StringSerializer(),
            JsonSerializer(JacksonUtils.enhancedObjectMapper())
        )
    }

    @Bean
    fun showKafkaTemplate(): KafkaTemplate<String, Show> {
        return KafkaTemplate(showProducerFactory())
    }
}