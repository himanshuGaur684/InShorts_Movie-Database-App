package dev.himanshu.inshorts.task.moviedatabaseapp.domain.useCases

import dev.himanshu.inshorts.task.moviedatabaseapp.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    operator fun invoke(query: String) = movieRepository.search(query)
}
