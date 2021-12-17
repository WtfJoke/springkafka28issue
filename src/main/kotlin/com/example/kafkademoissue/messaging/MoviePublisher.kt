package com.example.kafkademoissue.messaging

import com.example.kafkademoissue.data.Movie
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class MoviePublisher(
        private val moviePublishTemplate: KafkaTemplate<String, Movie>,
        @Value("\${movie.topic}")
        private val topicName: String,
) {

    fun publishMovie(movieToPublish: Movie) {
        moviePublishTemplate.send(topicName, movieToPublish.title, movieToPublish)
        println("Published ${movieToPublish.title} to $topicName")
    }
}