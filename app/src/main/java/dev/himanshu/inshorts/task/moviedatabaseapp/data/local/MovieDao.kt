package dev.himanshu.inshorts.task.moviedatabaseapp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.himanshu.inshorts.task.moviedatabaseapp.data.local.model.MovieEntity
import dev.himanshu.inshorts.task.moviedatabaseapp.data.local.model.Type
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface MovieDao {

    @Upsert
    fun upsert(movie: MovieEntity): Completable

    @Upsert
    fun upsertList(movies: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE type=:type")
    fun getAllMovies(type: String): Single<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity WHERE `query`=:query")
    fun getCachedQueryResponses(query: String): Single<List<MovieEntity>>

    @Query("DELETE FROM MovieEntity WHERE id=:id")
    fun deleteMovieEntity(id: Int): Completable

    @Query("DELETE FROM MovieEntity")
    fun nukeTable(): Completable

    @Query("SELECT * FROM MovieEntity WHERE type=:bookMark")
    fun getAllBookMarks(bookMark: String): Single<List<MovieEntity>>

    @Query("SELECT COUNT(*) FROM MovieEntity WHERE id=:movieId")
    fun getMovieCount(movieId: Int): Int

    @Query("SELECT * FROM MovieEntity WHERE id=:movieId AND type=:type")
    fun getMovie(movieId: Int, type: String = Type.BookMark.name): MovieEntity?
}
