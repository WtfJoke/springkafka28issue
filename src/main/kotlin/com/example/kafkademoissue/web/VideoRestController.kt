package com.example.kafkademoissue.web

import com.example.kafkademoissue.data.Movie
import com.example.kafkademoissue.data.Show
import com.example.kafkademoissue.messaging.publisher.MoviePublisher
import com.example.kafkademoissue.messaging.publisher.ShowPublisher
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class VideoRestController(
    val moviePublisher: MoviePublisher,
    val showPublisher: ShowPublisher
) {

    val movies = listOf(
        Movie("Inception"),
        Movie("Spider-Man"),
        Movie("Jojo Rabbit")
    )

    val shows = listOf(
        Show("La casa de papel"),
        Show("Silicon Valley"),
        Show("Prison Break"),
    )

    @PostMapping("/movie")
    fun publishMovie() {
        moviePublisher.publishMovie(movies.random())
    }

    @PostMapping("/show")
    fun publishShow() {
        showPublisher.publishShow(shows.random())
    }
}