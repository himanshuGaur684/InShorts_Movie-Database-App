package dev.himanshu.inshorts.task.moviedatabaseapp.presentation.dashboard

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.model.Movie
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.useCases.GetNowPlayingMoviesUseCase
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.useCases.GetTrendingMoviesUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.internal.schedulers.IoScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
) : ViewModel() {

    private val io by lazy { IoScheduler() }
    private val compositeDisposable = CompositeDisposable()

    private val _uiState = MutableStateFlow<DashboardUIState>(DashboardUIState.Idle)
    val uiState = _uiState.asStateFlow()

    fun getNowPlayingTime(): Pair<String, String> {
        val formatter = SimpleDateFormat("yyyy-dd-mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        val currentTime = calendar.timeInMillis
        calendar.add(Calendar.MONTH, -1)
        val oneMonthBefore = calendar.timeInMillis
        return Pair(formatter.format(oneMonthBefore), formatter.format(currentTime))
    }

    init {
        getDashboardData()
    }

    fun getDashboardData() {
        _uiState.update { DashboardUIState.Loading }
        val getNowPlayingTime = getNowPlayingTime()
        val singleZip = Single.zip(
            getTrendingMoviesUseCase(),
            getNowPlayingMoviesUseCase(
                minDate = getNowPlayingTime.first,
                maxDate = getNowPlayingTime.second,
            ),
        ) { trendingMovies, nowPlayingMovies ->
            Pair(trendingMovies, nowPlayingMovies)
        }.subscribeOn(io)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data ->
                    _uiState.update {
                        DashboardUIState.Data(
                            trendingMovies = data.first,
                            nowPlayingMovies = data.second,
                        )
                    }
                },
                { error ->
                    _uiState.update {
                        DashboardUIState.Error(
                            message = error.localizedMessage.toString(),
                        )
                    }
                },
            )
        compositeDisposable.add(singleZip)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

sealed interface DashboardUIState {
    data object Loading : DashboardUIState
    data class Data(
        val trendingMovies: List<Movie>? = null,
        val nowPlayingMovies: List<Movie>? = null,
    ) : DashboardUIState

    data class Error(val message: String) : DashboardUIState
    data object Idle : DashboardUIState
}
