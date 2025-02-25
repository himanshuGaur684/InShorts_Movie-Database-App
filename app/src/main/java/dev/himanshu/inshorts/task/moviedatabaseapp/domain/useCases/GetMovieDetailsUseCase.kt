package dev.himanshu.inshorts.task.moviedatabaseapp.domain.useCases

import dev.himanshu.inshorts.task.moviedatabaseapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    operator fun invoke(movieId: String) = movieRepository.getDetails(movieId)
}
