package dev.himanshu.inshorts.task.moviedatabaseapp.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.himanshu.inshorts.task.moviedatabaseapp.presentation.LocalNavHostController
import dev.himanshu.inshorts.task.moviedatabaseapp.presentation.bookMarks.BookMarkScreen
import dev.himanshu.inshorts.task.moviedatabaseapp.presentation.bookMarks.BookMarkViewModel
import dev.himanshu.inshorts.task.moviedatabaseapp.presentation.dashboard.DashboardScreen
import dev.himanshu.inshorts.task.moviedatabaseapp.presentation.dashboard.DashboardViewModel
import dev.himanshu.inshorts.task.moviedatabaseapp.presentation.details.MovieDetailsScreen
import dev.himanshu.inshorts.task.moviedatabaseapp.presentation.details.MovieDetailsViewModel
import dev.himanshu.inshorts.task.moviedatabaseapp.presentation.search.SearchScreen
import dev.himanshu.inshorts.task.moviedatabaseapp.presentation.search.SearchViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navHostController = LocalNavHostController.current
    NavHost(navHostController, startDestination = Dest.Dashboard) {
        composable<Dest.Dashboard> {
            val viewModel = hiltViewModel<DashboardViewModel>()

            DashboardScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel,
                onSearch = {
                    navHostController.navigate(Dest.Search)
                },
                goToBookMarks = {
                    navHostController.navigate(Dest.BookMark)
                },
                showDetails = {
                    navHostController.navigate(Dest.MovieDetails(it))
                },
            )
        }
        composable<Dest.Search> {
            val searchViewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = searchViewModel,
                showDetails = {
                    navHostController.navigate(Dest.MovieDetails(it))
                },
            )
        }
        composable<Dest.BookMark> {
            val bookMarkViewModel = hiltViewModel<BookMarkViewModel>()
            BookMarkScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = bookMarkViewModel,
                showDetails = {
                    navHostController.navigate(Dest.MovieDetails(it))
                },
            )
        }
        composable<Dest.MovieDetails> {
            val detailsViewModel = hiltViewModel<MovieDetailsViewModel>()
            MovieDetailsScreen(modifier = Modifier.fillMaxSize(), viewModel = detailsViewModel)
        }
    }
}
