package dev.himanshu.inshorts.task.moviedatabaseapp.data.remote.apiService

import dev.himanshu.inshorts.task.moviedatabaseapp.data.remote.model.details.MovieDetailsResponse
import dev.himanshu.inshorts.task.moviedatabaseapp.data.remote.model.nowPlaying.NowPlayingResponse
import dev.himanshu.inshorts.task.moviedatabaseapp.data.remote.model.search.SearchResponse
import dev.himanshu.inshorts.task.moviedatabaseapp.data.remote.model.trending.TrendingResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// curl --request GET \
//     --url 'https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&with_release_type=2|3&release_date.gte={min_date}&release_date.lte={max_date}' \
//     --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkYzcyNzdkNGE5MDhjNzg1M2Y5OTdmYjFkMGFkMDE5MSIsIm5iZiI6MTcyMDM2ODAyMC41MzMsInN1YiI6IjY2OGFiYjk0YWE5ZGUxNjE4OTlmMTQ0ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.OyJOyJU_wKolDcEVSPKHovIc1zAg941ru2QULL66SkA' \
//     --header 'accept: application/json'

interface ApiService {

    @GET("3/discover/movie")
    fun getNowPlayingMovies(
        @Query("release_date.gte") minimumDate: String,
        @Query("release_date.lte") maximumDate: String,
    ): Single<NowPlayingResponse>

    // https://api.themoviedb.org/3/trending/movie/day?language=en-US' \

    @GET("3/trending/movie/day")
    fun getTrendingMovies(
        @Query("language") language: String = "en-US",
    ): Single<TrendingResponse>

    @GET("3/search/movie")
    fun search(
        @Query("query") query: String,
    ): Single<SearchResponse>

    @GET("3/movie/{movie_id}")
    fun getDetails(
        @Path("movie_id") movieId: String,
    ): Single<MovieDetailsResponse>
}
