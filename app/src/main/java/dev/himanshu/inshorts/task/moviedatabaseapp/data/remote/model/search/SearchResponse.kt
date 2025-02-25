package dev.himanshu.inshorts.task.moviedatabaseapp.data.remote.model.search

import dev.himanshu.inshorts.task.moviedatabaseapp.data.remote.model.movie.MovieDTO

data class SearchResponse(
    val results: List<MovieDTO>,
)
