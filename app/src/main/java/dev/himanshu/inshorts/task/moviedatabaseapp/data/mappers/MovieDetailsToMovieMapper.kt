package dev.himanshu.inshorts.task.moviedatabaseapp.data.mappers

import dev.himanshu.inshorts.task.moviedatabaseapp.data.utils.Mapper
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.model.Movie
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.model.MovieDetails
import javax.inject.Inject

class MovieDetailsToMovieMapper @Inject constructor() : Mapper<MovieDetails, Movie> {
    override fun map(from: MovieDetails): Movie {
        return with(from) {
            Movie(
                id = id,
                title = title,
                imageUrl = posterPath,
                originalLanguage = original_language,
                originalTitle = original_title,
                overview = overview,
                popularity = popularity,
                posterPath = posterPath,
                releaseDate = release_date,
                voteAverage = vote_average,
                voteCount = vote_count,
                isBookMarked = isBookMarked,
            )
        }
    }
}
