package dev.himanshu.inshorts.task.moviedatabaseapp.presentation.bookMarks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.himanshu.inshorts.task.moviedatabaseapp.presentation.bookMarks.components.BookMarkListItem
import dev.himanshu.inshorts.task.moviedatabaseapp.presentation.utils.MovieLoader

private const val ANIMATION_DURATION_1000 = 1000
private const val ANIMATION_DURATION_500 = 500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookMarkScreen(
    modifier: Modifier = Modifier,
    viewModel: BookMarkViewModel,
    showDetails: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Bookmarks") },
            modifier = Modifier.fillMaxWidth(),
        )
    }) {
        AnimatedVisibility(
            uiState.isLoading,
            enter = slideInVertically(tween(ANIMATION_DURATION_1000)) { it } +
                fadeIn(tween(ANIMATION_DURATION_500)),
            exit = slideOutVertically(tween(ANIMATION_DURATION_1000)) { it } +
                fadeOut(tween(ANIMATION_DURATION_500)),
        ) {
            MovieLoader(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
            )
        }

        AnimatedVisibility(
            uiState.error.isNotEmpty(),
            enter = slideInVertically(tween(ANIMATION_DURATION_1000)) { it } +
                fadeIn(tween(ANIMATION_DURATION_500)),
            exit = slideOutVertically(tween(ANIMATION_DURATION_1000)) { it } +
                fadeOut(tween(ANIMATION_DURATION_500)),
        ) {
            Box(
                modifier
                    .padding(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(uiState.error)
            }
        }

        uiState.data?.let { movies ->

            AnimatedVisibility(
                movies.isEmpty(),
                enter = slideInVertically(tween(ANIMATION_DURATION_1000)) { it } +
                    fadeIn(tween(ANIMATION_DURATION_500)),
                exit = slideOutVertically(tween(ANIMATION_DURATION_1000)) { it } +
                    fadeOut(tween(ANIMATION_DURATION_500)),
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No movies found..")
                }
            }
            AnimatedVisibility(
                movies.isNotEmpty(),
                enter = slideInVertically(tween(ANIMATION_DURATION_1000)) { it } +
                    fadeIn(tween(ANIMATION_DURATION_500)),
                exit = slideOutVertically(tween(ANIMATION_DURATION_1000)) { it } +
                    fadeOut(tween(ANIMATION_DURATION_500)),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                ) {
                    items(movies) {
                        BookMarkListItem(
                            modifier = Modifier.fillMaxWidth(),
                            movie = it,
                            onDelete = {
                                viewModel.removeBookMarkMovie(it)
                            },
                            showDetails,
                        )
                    }
                }
            }
        }
    }
}
