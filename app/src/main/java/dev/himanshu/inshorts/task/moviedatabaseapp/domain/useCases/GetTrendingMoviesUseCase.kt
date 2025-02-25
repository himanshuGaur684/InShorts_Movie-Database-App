package dev.himanshu.inshorts.task.moviedatabaseapp.domain.useCases

import dev.himanshu.inshorts.task.moviedatabaseapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke() = movieRepository.getTrendingMovies()
}
