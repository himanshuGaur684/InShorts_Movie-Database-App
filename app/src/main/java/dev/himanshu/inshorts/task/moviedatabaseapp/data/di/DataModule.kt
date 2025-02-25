package dev.himanshu.inshorts.task.moviedatabaseapp.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.himanshu.inshorts.task.moviedatabaseapp.data.local.AppDatabase
import dev.himanshu.inshorts.task.moviedatabaseapp.data.local.MovieDao
import dev.himanshu.inshorts.task.moviedatabaseapp.data.mappers.MovieDetailsResponseToMovieMapper
import dev.himanshu.inshorts.task.moviedatabaseapp.data.mappers.MovieEntityToMovieMapper
import dev.himanshu.inshorts.task.moviedatabaseapp.data.remote.apiService.ApiService
import dev.himanshu.inshorts.task.moviedatabaseapp.data.repository.MovieRepositoryImpl
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.repository.MovieRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.themoviedb.org/"

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkYzcyNzdkNGE5MDhjNzg1M2Y5OTdmYjFkMGFkMDE5MSIsIm5iZiI6MTcyMDM2ODAyMC41MzMsInN1YiI6IjY2OGFiYjk0YWE5ZGUxNjE4OTlmMTQ0ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.OyJOyJU_wKolDcEVSPKHovIc1zAg941ru2QULL66SkA",
                    )
                    .build()
                chain.proceed(request)
            }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.getMovieDao()
    }

    @Provides
    fun provideMovieRepository(
        apiService: ApiService,
        domainMapper: MovieEntityToMovieMapper,
        detailsDomainMapper: MovieDetailsResponseToMovieMapper,
        dao: MovieDao,
    ): MovieRepository {
        return MovieRepositoryImpl(
            apiService = apiService,
            domainMapper = domainMapper,
            dao = dao,
            detailsDomainMapper = detailsDomainMapper,
        )
    }
}
