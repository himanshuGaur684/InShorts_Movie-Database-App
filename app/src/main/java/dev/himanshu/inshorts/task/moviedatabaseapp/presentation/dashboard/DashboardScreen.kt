package dev.himanshu.inshorts.task.moviedatabaseapp.presentation.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.model.Movie
import dev.himanshu.inshorts.task.moviedatabaseapp.presentation.utils.MovieLoader

private const val ANIMATION_DURATION_1000 = 1000
private const val ANIMATION_DURATION_500 = 500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel,
    onSearch: () -> Unit,
    goToBookMarks: () -> Unit,
    showDetails: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = { Text("Movies") },
            actions = {
                IconButton(onClick = onSearch) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                }
            },
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            goToBookMarks()
        }) {
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                tint = Color.Black,
            )
        }
    }) {
        Column(
            modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            AnimatedVisibility(
                uiState is DashboardUIState.Loading,
                enter = fadeIn(tween(ANIMATION_DURATION_500)),
                exit = fadeOut(tween(ANIMATION_DURATION_500)),
            ) {
                MovieLoader(
                    modifier = modifier
                        .fillMaxSize()
                        .background(color = Color.LightGray),
                )
            }

            AnimatedVisibility(
                uiState is DashboardUIState.Data,
                enter = slideInVertically(tween(ANIMATION_DURATION_1000)) { it } +
                    fadeIn(tween(ANIMATION_DURATION_500)),
                exit = slideOutVertically(tween(ANIMATION_DURATION_1000)) { it } +
                    fadeOut(tween(ANIMATION_DURATION_500)),
            ) {
                Text(
                    text = "Now Playing",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                )
            }

            AnimatedVisibility(
                uiState is DashboardUIState.Error,
                enter = fadeIn(tween(ANIMATION_DURATION_500)),
                exit = fadeOut(tween(ANIMATION_DURATION_500)),
            ) {
                val message = uiState as DashboardUIState.Error
                Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(message.toString(), style = MaterialTheme.typography.bodyMedium)
                }
            }

            AnimatedVisibility(
                uiState is DashboardUIState.Data,
                enter = slideInVertically(tween(ANIMATION_DURATION_1000)) { it } +
                    fadeIn(tween(ANIMATION_DURATION_500)),
                exit = slideOutVertically(tween(ANIMATION_DURATION_1000)) { it } +
                    fadeOut(tween(ANIMATION_DURATION_500)),
            ) {
                val resultPair = uiState as DashboardUIState.Data
                resultPair.trendingMovies?.let {
                    TrendingMoviesList(
                        modifier = Modifier.fillMaxWidth(),
                        list = resultPair.trendingMovies,
                    ) {
                        showDetails(it)
                    }
                }
            }

            AnimatedVisibility(
                uiState is DashboardUIState.Data,
                enter = slideInVertically(tween(ANIMATION_DURATION_1000)) { it } +
                    fadeIn(tween(ANIMATION_DURATION_500)),
                exit = slideOutVertically(tween(ANIMATION_DURATION_1000)) { it } +
                    fadeOut(tween(ANIMATION_DURATION_500)),
            ) {
                Text(
                    text = "Trending Movies",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                )
            }

            AnimatedVisibility(
                uiState is DashboardUIState.Data,
                enter = slideInVertically(tween(ANIMATION_DURATION_1000)) { it },
                exit = slideOutVertically(tween(ANIMATION_DURATION_1000)) { it },
            ) {
                val resultPair = uiState as DashboardUIState.Data
                resultPair.nowPlayingMovies?.let {
                    NowPlayingMovies(
                        modifier = Modifier.fillMaxWidth(),
                        list = resultPair.nowPlayingMovies,
                    ) {
                        showDetails(it)
                    }
                }
            }
        }
    }
}

@Composable
fun ColumnScope.TrendingMoviesList(
    modifier: Modifier = Modifier,
    list: List<Movie>,
    showDetails: (String) -> Unit,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        itemsIndexed(list) { index, item ->
            AsyncImage(
                modifier = Modifier
                    .padding(
                        start = if (index == 0) 16.dp else 0.dp,
                        end = if (index == list.size.minus(1)) 0.dp else 16.dp,
                    )
                    .clickable {
                        showDetails.invoke(item.id.toString())
                    }
                    .width(200.dp)
                    .height(250.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
                    .clip(shape = RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                model = item.imageUrl,
            )
        }
    }
}

@Composable
fun NowPlayingMovies(
    modifier: Modifier = Modifier,
    list: List<Movie>,
    showDetails: (String) -> Unit,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        itemsIndexed(list) { index, item ->
            AsyncImage(
                modifier = Modifier
                    .padding(
                        start = if (index == 0) 16.dp else 0.dp,
                        end = if (index == list.size.minus(1)) 0.dp else 16.dp,
                    )
                    .clickable { showDetails(item.id.toString()) }
                    .width(200.dp)
                    .height(250.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
                    .clip(shape = RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                model = item.imageUrl,
            )
        }
    }
}
