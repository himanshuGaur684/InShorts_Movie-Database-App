package dev.himanshu.inshorts.task.moviedatabaseapp.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import dev.himanshu.inshorts.task.moviedatabaseapp.R
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.model.Movie
import dev.himanshu.inshorts.task.moviedatabaseapp.presentation.utils.MovieLoader

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    showDetails: (String) -> Unit,
) {
    var query by rememberSaveable { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val searchFocusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        searchFocusRequester.requestFocus()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TextField(
                value = query,
                onValueChange = {
                    query = it
                    viewModel.update(query)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(searchFocusRequester),
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                placeholder = { Text("Search...", color = Color.LightGray) },
            )
        },
    ) {
        if (uiState.isLoading) {
            MovieLoader(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
            )
        }

        if (uiState.error.isNotEmpty()) {
            Box(
                Modifier
                    .padding(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(uiState.error)
            }
        }

        uiState.data?.let { movies ->

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
            ) {
                items(movies) { movie ->
                    MovieListItem(movie = movie) {
                        showDetails(it)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieListItem(modifier: Modifier = Modifier, movie: Movie, onClick: (String) -> Unit) {
    AsyncImage(
        modifier = modifier
            .clickable { onClick(movie.id.toString()) }
            .padding(8.dp)
            .fillMaxWidth()
            .height(250.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        model = movie.imageUrl,
        error = painterResource(R.drawable.error),
    )
}
