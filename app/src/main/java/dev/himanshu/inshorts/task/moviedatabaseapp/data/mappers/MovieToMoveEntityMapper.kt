package dev.himanshu.inshorts.task.moviedatabaseapp.data.mappers

import dev.himanshu.inshorts.task.moviedatabaseapp.data.local.model.MovieEntity
import dev.himanshu.inshorts.task.moviedatabaseapp.data.local.model.Type
import dev.himanshu.inshorts.task.moviedatabaseapp.data.utils.Mapper
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.model.Movie

class MovieToMoveEntityMapper(
    private val type: Type,
    private val query: String?,
) : Mapper<Movie, MovieEntity> {
    override fun map(from: Movie): MovieEntity {
        return with(from) {
            MovieEntity(
                id = id,
                originalLanguage = originalLanguage,
                originalTitle = originalTitle,
                overview = overview,
                popularity = popularity,
                posterPath = posterPath,
                releaseDate = releaseDate,
                title = title,
                voteAverage = voteAverage,
                voteCount = voteCount,
                type = type.name,
                query = query,
            )
        }
    }
}
