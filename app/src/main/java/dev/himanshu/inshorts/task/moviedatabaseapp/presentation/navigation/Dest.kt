package dev.himanshu.inshorts.task.moviedatabaseapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Dest {
    @Serializable
    data object Dashboard : Dest

    @Serializable
    data object Search : Dest

    @Serializable
    data object BookMark : Dest

    @Serializable
    data class MovieDetails(val id: String) : Dest
}
