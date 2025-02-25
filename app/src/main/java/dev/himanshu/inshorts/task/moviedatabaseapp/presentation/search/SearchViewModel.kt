package dev.himanshu.inshorts.task.moviedatabaseapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.model.Movie
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.useCases.BookMarkUseCase
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.useCases.SearchMoviesUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val bookMarkUseCase: BookMarkUseCase,
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _query = MutableStateFlow("")

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _query.filter { it.isNotEmpty() }
                .debounce(1000)
                .collectLatest { query ->
                    search(query)
                }
        }
    }

    fun update(query: String) {
        _query.update { query }
    }

    fun bookMark(movie: Movie) {
        bookMarkUseCase(movie)
    }

    fun search(query: String) {
        _uiState.update { SearchUiState(isLoading = true) }
        val searchDispose = searchMoviesUseCase(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movies ->
                _uiState.update { SearchUiState(data = movies) }
            }, { error ->
                _uiState.update { SearchUiState(error = error.localizedMessage.toString()) }
            })
        disposables.add(searchDispose)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}

data class SearchUiState(
    val isLoading: Boolean = false,
    val data: List<Movie>? = null,
    val error: String = "",
)
