package dev.himanshu.inshorts.task.moviedatabaseapp.data.mappers

import dev.himanshu.inshorts.task.moviedatabaseapp.data.remote.model.details.MovieDetailsResponse
import dev.himanshu.inshorts.task.moviedatabaseapp.data.utils.Mapper
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.model.Movie
import javax.inject.Inject

class MovieDetailsResponseToMovieMapper @Inject constructor() :
    Mapper<MovieDetailsResponse, Movie> {
    override fun map(from: MovieDetailsResponse): Movie {
        return with(from) {
            Movie(
                id = id,
                title = title,
                imageUrl = "https://image.tmdb.org/t/p/original".plus(from.poster_path),
                originalLanguage = original_language,
                originalTitle = original_title,
                overview = overview,
                popularity = popularity,
                posterPath = "https://image.tmdb.org/t/p/original".plus(from.poster_path),
                releaseDate = release_date,
                voteAverage = vote_average,
                voteCount = vote_count,
                isBookMarked = false,
            )
        }
    }
}
