package dev.himanshu.inshorts.task.moviedatabaseapp.data.repository

import dev.himanshu.inshorts.task.moviedatabaseapp.data.local.MovieDao
import dev.himanshu.inshorts.task.moviedatabaseapp.data.local.model.Type
import dev.himanshu.inshorts.task.moviedatabaseapp.data.mappers.MovieDTOToMovieEntityMapper
import dev.himanshu.inshorts.task.moviedatabaseapp.data.mappers.MovieDetailsResponseToMovieMapper
import dev.himanshu.inshorts.task.moviedatabaseapp.data.mappers.MovieEntityToMovieMapper
import dev.himanshu.inshorts.task.moviedatabaseapp.data.mappers.MovieToMoveEntityMapper
import dev.himanshu.inshorts.task.moviedatabaseapp.data.remote.apiService.ApiService
import dev.himanshu.inshorts.task.moviedatabaseapp.data.utils.mapAll
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.model.Movie
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class MovieRepositoryImpl(
    private val apiService: ApiService,
    private val domainMapper: MovieEntityToMovieMapper,
    private val detailsDomainMapper: MovieDetailsResponseToMovieMapper,
    private val dao: MovieDao,
) : MovieRepository {

    override fun getTrendingMovies(): Single<List<Movie>> {
        val mapper = MovieDTOToMovieEntityMapper(Type.Trending, query = null)
        return dao.getAllMovies(Type.Trending.name)
            .flatMap { entites ->
                if (entites.isNotEmpty()) {
                    Single.just(domainMapper.mapAll(entites))
                } else {
                    apiService.getTrendingMovies()
                        .flatMap { response ->
                            val entities = mapper.mapAll(response.results)
                            dao.upsertList(entities)
                            dao.getAllMovies(type = Type.Trending.name)
                        }.map { movieEntites ->
                            domainMapper.mapAll(movieEntites)
                        }
                }
            }
    }

    override fun getNowPlaying(
        minimumDate: String,
        maximumDate: String,
    ): Single<List<Movie>> {
        val mapper = MovieDTOToMovieEntityMapper(Type.NowPlaying, query = null)

        return dao.getAllMovies(type = Type.NowPlaying.name)
            .flatMap { entities ->
                if (entities.isNotEmpty()) {
                    Single.just(domainMapper.mapAll(entities))
                } else {
                    apiService.getNowPlayingMovies(
                        minimumDate = minimumDate,
                        maximumDate = maximumDate,
                    ).flatMap { response ->
                        val entities = mapper.mapAll(response.results)
                        dao.upsertList(entities)
                        dao.getAllMovies(Type.NowPlaying.name)
                    }.map { movieEntities ->
                        domainMapper.mapAll(movieEntities)
                    }
                }
            }
    }

    override fun search(query: String): Single<List<Movie>> {
        val entityMapper = MovieDTOToMovieEntityMapper(type = Type.Query, query = query)
        return dao.getCachedQueryResponses(query.trim())
            .flatMap { cachedEntities ->
                if (cachedEntities.isNotEmpty()) {
                    val movies = domainMapper.mapAll(cachedEntities)
                    Single.just(movies)
                } else {
                    apiService.search(query)
                        .flatMap { response ->
                            val entities = entityMapper.mapAll(response.results)
                            dao.upsertList(entities)
                            dao.getCachedQueryResponses(query = query)
                        }.map { entities ->
                            domainMapper.mapAll(entities)
                        }
                }
            }.onErrorResumeNext { error ->
                Single.error(error)
            }
    }

    override fun bookmark(movie: Movie): Completable {
        val mapper = MovieToMoveEntityMapper(
            type = Type.BookMark,
            query = null,
        )
        val entity = mapper.map(movie)
        return dao.upsert(entity)
    }

    override fun getAllCachedMovies(query: String): Single<List<Movie>> {
        val mapper = MovieEntityToMovieMapper()
        return dao.getCachedQueryResponses(query = query)
            .map { entities -> mapper.mapAll(entities) }
    }

    override fun removeBookMark(movie: Movie): Completable {
        return dao.deleteMovieEntity(movie.id)
    }

    override fun getAllBookmarks(): Single<List<Movie>> {
        val mapper = MovieEntityToMovieMapper()
        return dao.getAllBookMarks(Type.BookMark.name)
            .map { movieEntities -> mapper.mapAll(movieEntities) }
    }

    override fun getDetails(movieId: String): Single<Movie> {
        return apiService.getDetails(movieId)
            .flatMap<Movie> { result ->
                val cachedMovies = dao.getMovie(movieId.toInt())
                val details = detailsDomainMapper.map(result)
                Single.just(details.copy(isBookMarked = cachedMovies != null))
            }
    }
}
