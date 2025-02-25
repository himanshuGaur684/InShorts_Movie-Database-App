package dev.himanshu.inshorts.task.moviedatabaseapp.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.himanshu.inshorts.task.moviedatabaseapp.data.mappers.MovieDetailsToMovieMapper
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.model.Movie
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.model.MovieDetails
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.useCases.BookMarkUseCase
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.useCases.GetMovieDetailsUseCase
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.useCases.RemoveBookMarkUseCase
import dev.himanshu.inshorts.task.moviedatabaseapp.presentation.navigation.Dest
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val removeBookMarkUseCase: RemoveBookMarkUseCase,
    private val bookMarkMovieUseCase: BookMarkUseCase,
    private val mapper: MovieDetailsToMovieMapper,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val movieId = savedStateHandle.toRoute<Dest.MovieDetails>().id
        getDetails(movieId)
    }

    fun removeBookMark(movie: Movie) {
        removeBookMarkUseCase(movie)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun bookMark(movie: Movie) {
        bookMarkMovieUseCase(movie)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun getDetails(movieId: String) {
        _uiState.update { MovieDetailsUiState(isLoading = true) }
        val dispose = getMovieDetailsUseCase(movieId)
            .subscribe({ details ->
                _uiState.update { MovieDetailsUiState(data = details) }
            }, { error ->
                _uiState.update { MovieDetailsUiState(error = error.localizedMessage.toString()) }
            })
        disposable.add(dispose)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}

data class MovieDetailsUiState(
    val isLoading: Boolean = false,
    val data: Movie? = null,
    val error: String = "",
)
