package dev.himanshu.inshorts.task.moviedatabaseapp.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val imageUrl: String,

    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,

    val isBookMarked: Boolean,

)
