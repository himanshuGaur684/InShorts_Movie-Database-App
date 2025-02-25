package dev.himanshu.inshorts.task.moviedatabaseapp.data.remote.model.nowPlaying

import dev.himanshu.inshorts.task.moviedatabaseapp.data.remote.model.movie.MovieDTO

data class NowPlayingResponse(
    val dates: DatesDTO,
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int,
)
