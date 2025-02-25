package dev.himanshu.inshorts.task.moviedatabaseapp.domain.useCases

import dev.himanshu.inshorts.task.moviedatabaseapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(minDate: String, maxDate: String) = movieRepository.getNowPlaying(
        minimumDate = minDate,
        maximumDate = maxDate,
    )
}
