package com.example.kafkademoissue.messaging

import com.example.kafkademoissue.data.Show
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ShowConsumer {

    @KafkaListener(topics = ["PUBLISHED_SHOWS"], containerFactory = "showPublishedKafkaListenerContainerFactory")
    fun listenToShowUpdates(show: Show) {
        println("I watched show: $show")
    }
}