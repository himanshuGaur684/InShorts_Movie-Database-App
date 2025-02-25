package dev.himanshu.inshorts.task.moviedatabaseapp.domain.useCases

import dev.himanshu.inshorts.task.moviedatabaseapp.domain.model.Movie
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.repository.MovieRepository
import javax.inject.Inject

class BookMarkUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    operator fun invoke(movie: Movie) = movieRepository.bookmark(movie)
}
