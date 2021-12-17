package com.example.kafkademoissue.messaging.publisher

import com.example.kafkademoissue.data.Show
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ShowPublisher(
    private val showPublisher: KafkaTemplate<String, Show>,
    @Value("\${show.topic}")
    private val topic: String,
) {

    fun publishShow(showToPublish: Show) {
        showPublisher.send(topic, showToPublish.title, showToPublish)
        println("Published ${showToPublish.title} to $topic")
    }
}