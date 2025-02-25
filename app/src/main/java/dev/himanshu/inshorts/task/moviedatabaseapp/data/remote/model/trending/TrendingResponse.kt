package dev.himanshu.inshorts.task.moviedatabaseapp.data.remote.model.trending

import dev.himanshu.inshorts.task.moviedatabaseapp.data.remote.model.movie.MovieDTO

data class TrendingResponse(
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int,
)
