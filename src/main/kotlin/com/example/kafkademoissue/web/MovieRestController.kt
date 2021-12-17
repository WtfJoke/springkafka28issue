package com.example.kafkademoissue.web

import com.example.kafkademoissue.data.Movie
import com.example.kafkademoissue.messaging.MoviePublisher
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MovieRestController(val moviePublisher: MoviePublisher) {

    val movies = listOf(
            Movie("Inception"),
            Movie("Spider-Man"),
            Movie("Jojo Rabbit")
    )

    @PostMapping("/movie")
    fun publishMovie() {
        moviePublisher.publishMovie(movies.random())
    }
}