package dev.himanshu.inshorts.task.moviedatabaseapp.domain.model

data class MovieDetails(
    val adult: Boolean,
    val backdropPath: String,
    val budget: Int,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,

    val isBookMarked: Boolean = false,

)
