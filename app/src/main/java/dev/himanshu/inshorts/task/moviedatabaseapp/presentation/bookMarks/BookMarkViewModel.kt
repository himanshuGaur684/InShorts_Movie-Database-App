package dev.himanshu.inshorts.task.moviedatabaseapp.presentation.bookMarks

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.model.Movie
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.useCases.GetBookMarkMoviesUseCase
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.useCases.RemoveBookMarkUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val getBookMarkMoviesUseCase: GetBookMarkMoviesUseCase,
    private val deleteBookMarkUseCase: RemoveBookMarkUseCase,
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _uiState = MutableStateFlow<BookMarkUiState>(BookMarkUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getBookMarks()
    }

    fun getBookMarks() {
        _uiState.update { BookMarkUiState(isLoading = true) }
        val bookMark = getBookMarkMoviesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movies ->
                _uiState.update { BookMarkUiState(data = movies) }
            }, { error ->
                _uiState.update { BookMarkUiState(error = error.localizedMessage.toString()) }
            })
        disposables.add(bookMark)
    }

    fun removeBookMarkMovie(movie: Movie) {
        val dispose = deleteBookMarkUseCase(movie)
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getBookMarks()
            })
        disposables.add(dispose)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}

data class BookMarkUiState(
    val isLoading: Boolean = false,
    val data: List<Movie>? = null,
    val error: String = "",
)
