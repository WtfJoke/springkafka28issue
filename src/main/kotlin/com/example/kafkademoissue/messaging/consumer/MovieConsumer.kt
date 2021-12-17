package com.example.kafkademoissue.messaging.consumer

import com.example.kafkademoissue.data.Movie
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class MovieConsumer {

    @KafkaListener(topics = ["\${movie.topic}"], containerFactory = "moviePublishedKafkaListenerContainerFactory")
    fun listenToMoviesUpdates(movie: Movie) {
        println("I watched a film: $movie")
    }
}