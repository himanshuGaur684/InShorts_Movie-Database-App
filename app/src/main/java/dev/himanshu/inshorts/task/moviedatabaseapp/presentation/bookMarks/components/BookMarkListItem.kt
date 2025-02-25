package dev.himanshu.inshorts.task.moviedatabaseapp.presentation.bookMarks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.himanshu.inshorts.task.moviedatabaseapp.domain.model.Movie

@Composable
fun BookMarkListItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onDelete: (Movie) -> Unit,
    showDetails: (String) -> Unit,
) {
    Card(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp,
        ),
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Color.White),
        ) {
            Column(
                modifier = Modifier
                    .clickable { showDetails(movie.id.toString()) }
                    .fillMaxWidth(),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(color = Color.Transparent, shape = RoundedCornerShape(12.dp))
                        .clip(shape = RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    model = movie.imageUrl,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    text = movie.title,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.Black,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black,
                )
                Spacer(Modifier.height(12.dp))
            }

            IconButton(
                onClick = {
                    onDelete(movie)
                },
                modifier = Modifier
                    .padding(end = 16.dp, top = 16.dp)
                    .size(45.dp)
                    .background(color = Color.White, shape = CircleShape)
                    .clip(shape = CircleShape)
                    .align(Alignment.TopEnd),

            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookMarkListItemPreview(modifier: Modifier = Modifier) {
    BookMarkListItem(
        modifier = modifier,
        movie = Movie(
            id = 12,
            title = "this is title",
            imageUrl = "TODO()",
            originalLanguage = "TODO()",
            originalTitle = "TODO()",
            overview = "This is the overview of the movie",
            popularity = TODO(),
            posterPath = "TODO()",
            releaseDate = "TODO()",
            voteAverage = 12.0,
            voteCount = 34,
            isBookMarked = true,
        ),
        onDelete = {},
        showDetails = {},
    )
}
