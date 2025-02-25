package dev.himanshu.inshorts.task.moviedatabaseapp.presentation.details

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import dev.himanshu.inshorts.task.moviedatabaseapp.presentation.LocalNavHostController
import dev.himanshu.inshorts.task.moviedatabaseapp.presentation.utils.MovieLoader

private const val ANIMATION_DURATION_1000 = 700
private const val ANIMATION_DURATION_500 = 500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailsViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navHostController = LocalNavHostController.current
    AnimatedVisibility(
        uiState.isLoading,
        enter = slideInVertically(tween(ANIMATION_DURATION_1000)) { it } +
            fadeIn(tween(ANIMATION_DURATION_500)),
        exit = slideOutVertically(tween(ANIMATION_DURATION_1000)) { it } +
            fadeOut(tween(ANIMATION_DURATION_500)),
    ) {
        MovieLoader(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.LightGray),
        )
    }

    AnimatedVisibility(
        uiState.error.isNotEmpty(),
        enter = slideInVertically(tween(ANIMATION_DURATION_1000)) { it } +
            fadeIn(tween(ANIMATION_DURATION_500)),
        exit = slideOutVertically(tween(ANIMATION_DURATION_1000)) { it } +
            fadeOut(tween(ANIMATION_DURATION_500)),
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(uiState.error)
        }
    }

    AnimatedVisibility(
        uiState.data != null,
        enter = slideInVertically(tween(ANIMATION_DURATION_1000)) { 100 } +
            fadeIn(tween(ANIMATION_DURATION_500)),
        exit = slideOutVertically(tween(ANIMATION_DURATION_1000)) { 100 } +
            fadeOut(tween(ANIMATION_DURATION_500)),
    ) {
        uiState.data?.let { details ->
            var isBookMarked by rememberSaveable { mutableStateOf(details.isBookMarked) }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {},
                        navigationIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    navHostController.popBackStack()
                                },
                            )
                        },
                        actions = {
                            IconButton(onClick = {
                                if (details.isBookMarked) {
                                    viewModel.removeBookMark(details.copy(isBookMarked = details.isBookMarked.not()))
                                    isBookMarked = false
                                } else {
                                    viewModel.bookMark(details.copy(isBookMarked = details.isBookMarked.not()))
                                    isBookMarked = true
                                }
                            }) {
                                Icon(
                                    imageVector = if (isBookMarked) Icons.Default.Delete else Icons.Outlined.Star,
                                    contentDescription = null,
                                    tint = Color.Unspecified,
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
            ) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(12.dp),
                            ),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        model = details.posterPath,
                    )
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                    ) {
                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = details.title,
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = details.originalTitle,
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        Spacer(Modifier.height(8.dp))

                        Text(text = details.overview)
                    }
                }
            }
        }
    }
}
