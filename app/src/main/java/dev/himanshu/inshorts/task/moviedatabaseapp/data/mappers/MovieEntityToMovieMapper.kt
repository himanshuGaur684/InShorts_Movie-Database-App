package dev.himanshu.inshorts.task.moviedatabaseapp.data.mappers

import dev.himanshu.inshorts.task.moviedatabaseapp.data.local.model.MovieEntity
import dev.himanshu.inshorts.task.moviedatabaseapp.data.local.model.Type
import dev.himanshu.inshorts.task.moviedatabaseapp.data.utils.Mapper
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.model.Movie
import javax.inject.Inject

class MovieEntityToMovieMapper @Inject constructor() : Mapper<MovieEntity, Movie> {
    override fun map(from: MovieEntity): Movie {
        return with(from) {
            Movie(
                id = id,
                title = title,
                imageUrl = "https://image.tmdb.org/t/p/original".plus(from.posterPath),
                originalLanguage = originalLanguage,
                originalTitle = originalTitle,
                overview = overview,
                popularity = popularity,
                posterPath = posterPath,
                releaseDate = releaseDate,
                voteAverage = voteAverage,
                voteCount = voteCount,
                isBookMarked = type == Type.BookMark.name,
            )
        }
    }
}
