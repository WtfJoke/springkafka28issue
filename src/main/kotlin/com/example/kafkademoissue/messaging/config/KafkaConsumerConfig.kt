package com.example.kafkademoissue.messaging.config

import com.example.kafkademoissue.data.Movie
import com.example.kafkademoissue.data.Show
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfig(
        private val springKafkaProperties: KafkaProperties,
) {

    @Bean
    fun showPublishedConsumerFactory(): ConsumerFactory<String, Show> {
        return DefaultKafkaConsumerFactory(
                consumerConfigs(), ErrorHandlingDeserializer(StringDeserializer()),
                ErrorHandlingDeserializer(JsonDeserializer(Show::class.java, false))
        )
    }

    @Bean
    fun showPublishedKafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Show>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Show>()
        factory.consumerFactory = showPublishedConsumerFactory()
        return factory
    }

    @Bean
    fun moviePublishedConsumerFactory(): ConsumerFactory<String, Movie> {
        return DefaultKafkaConsumerFactory(consumerConfigs())
    }

    @Bean
    fun moviePublishedKafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Movie>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Movie>()
        factory.consumerFactory = moviePublishedConsumerFactory()
        return factory
    }

    private fun consumerConfigs(): Map<String, Any> {
        return springKafkaProperties.buildConsumerProperties()
    }
}