package dev.himanshu.inshorts.task.moviedatabaseapp.domain.repository

import dev.himanshu.inshorts.task.moviedatabaseapp.domain.model.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface MovieRepository {

    fun getTrendingMovies(): Single<List<Movie>>

    fun getNowPlaying(
        minimumDate: String,
        maximumDate: String,
    ): Single<List<Movie>>

    fun search(query: String): Single<List<Movie>>

    fun bookmark(movie: Movie): Completable

    fun removeBookMark(movie: Movie): Completable

    fun getAllCachedMovies(query: String): Single<List<Movie>>

    fun getAllBookmarks(): Single<List<Movie>>

    fun getDetails(movieId: String): Single<Movie>
}
