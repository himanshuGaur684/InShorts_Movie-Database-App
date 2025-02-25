package dev.himanshu.inshorts.task.moviedatabaseapp.data.mappers

import dev.himanshu.inshorts.task.moviedatabaseapp.data.local.model.MovieEntity
import dev.himanshu.inshorts.task.moviedatabaseapp.data.local.model.Type
import dev.himanshu.inshorts.task.moviedatabaseapp.data.remote.model.movie.MovieDTO
import dev.himanshu.inshorts.task.moviedatabaseapp.data.utils.Mapper

class MovieDTOToMovieEntityMapper(
    private val type: Type,
    private val query: String?,
) : Mapper<MovieDTO, MovieEntity> {
    override fun map(from: MovieDTO): MovieEntity {
        return with(from) {
            MovieEntity(
                id = id,
                originalLanguage = original_language,
                originalTitle = original_title,
                overview = overview,
                popularity = popularity,
                posterPath = poster_path.orEmpty(),
                releaseDate = release_date,
                title = title,
                voteAverage = vote_average,
                voteCount = vote_count,
                type = type.name,
                query = query,
            )
        }
    }
}
