package com.geek.cinemax.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.geek.cinemax.AppConstants.MOVIES
import com.geek.cinemax.AppConstants.ROUTE_DETAIL_ARG_NAME
import com.geek.cinemax.AppConstants.ROUTE_MOVIE_DETAIL_PATH
import com.geek.cinemax.AppConstants.ROUTE_TV_DETAIL_PATH
import com.geek.cinemax.AppConstants.SEARCH_MOVIES
import com.geek.cinemax.AppConstants.SEARCH_TV
import com.geek.cinemax.AppConstants.TV
import com.geek.cinemax.ui.detail.movie.MovieDetailScreen
import com.geek.cinemax.ui.detail.movie.MovieDetailViewModel
import com.geek.cinemax.ui.detail.tv.TvDetailScreen
import com.geek.cinemax.ui.detail.tv.TvDetailViewModel
import com.geek.cinemax.ui.list.movie.MovieListScreen
import com.geek.cinemax.ui.list.movie.MovieListViewModel
import com.geek.cinemax.ui.list.tv.TvListScreen
import com.geek.cinemax.ui.list.tv.TvListViewModel
import com.geek.cinemax.ui.search.movie.MovieSearchScreen
import com.geek.cinemax.ui.search.movie.MovieSearchViewModel
import com.geek.cinemax.ui.search.tv.TvSearchScreen
import com.geek.cinemax.ui.search.tv.TvSearchViewModel
import com.geek.cinemax.ui.theme.TrailersComposeTheme
import com.geek.cinemax.utils.navigateToMovieDetail
import com.geek.cinemax.utils.navigateToTvDetail
import com.geek.cinemax.utils.navigateToVideo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrailersComposeTheme {
                val navController = rememberNavController()

                val movieListViewModel = hiltViewModel<MovieListViewModel>()
                val movies = movieListViewModel.movies.collectAsLazyPagingItems()
                val selectedMovieCategory =
                    movieListViewModel.selectedCategory.collectAsStateWithLifecycle(
                        lifecycleOwner = LocalLifecycleOwner.current
                    )

                val tvListViewModel = hiltViewModel<TvListViewModel>()
                val tvList = tvListViewModel.tvSeriesList.collectAsLazyPagingItems()
                val selectedTvCategory =
                    tvListViewModel.selectedCategory.collectAsStateWithLifecycle(
                        lifecycleOwner = LocalLifecycleOwner.current
                    )

                SharedTransitionLayout {
                    NavHost(
                        navController = navController,
                        startDestination = MOVIES,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable(route = MOVIES) {
                            MovieListScreen(
                                movies = movies,
                                onItemClicked = { navController.navigateToMovieDetail(it) },
                                selectedCategory = selectedMovieCategory.value,
                                onCategorySelected = {
                                    movieListViewModel.updateCategory(it)
                                    movies.refresh()
                                },
                                onTvMenuSelected = { navController.navigate(TV) },
                                onSearchSelected = { navController.navigate(SEARCH_MOVIES) },
                                animatedContentScope = this@composable
                            )
                        }
                        composable(
                            route = ROUTE_MOVIE_DETAIL_PATH,
                            arguments = listOf(
                                navArgument(ROUTE_DETAIL_ARG_NAME) { type = NavType.LongType },
                            )
                        ) {
                            val context = LocalContext.current
                            MovieDetailScreen(
                                viewModel = hiltViewModel<MovieDetailViewModel>(),
                                onItemClicked = { navController.navigateToMovieDetail(it) },
                                onVideoItemClicked = { navigateToVideo(context, it) },
                                animatedContentScope = this@composable
                            )
                        }
                        composable(route = TV) {
                            TvListScreen(
                                tvSeriesList = tvList,
                                onItemClicked = { navController.navigateToTvDetail(it) },
                                selectedCategory = selectedTvCategory.value,
                                onCategorySelected = {
                                    tvListViewModel.updateCategory(it)
                                    tvList.refresh()
                                },
                                onMenuItemSelected = { navController.navigate(MOVIES) },
                                onSearchSelected = { navController.navigate(SEARCH_TV) },
                                animatedContentScope = this@composable
                            )
                        }
                        composable(
                            route = ROUTE_TV_DETAIL_PATH,
                            arguments = listOf(
                                navArgument(ROUTE_DETAIL_ARG_NAME) { type = NavType.LongType },
                            )
                        ) {
                            val context = LocalContext.current
                            TvDetailScreen(
                                viewModel = hiltViewModel<TvDetailViewModel>(),
                                onItemClicked = { navController.navigateToTvDetail(it) },
                                onVideoItemClicked = { navigateToVideo(context, it) },
                                animatedContentScope = this@composable
                            )
                        }
                        composable(SEARCH_MOVIES) {
                            MovieSearchScreen(
                                viewModel = hiltViewModel<MovieSearchViewModel>(),
                                onBackButtonClicked = { navController.navigateUp() },
                                onItemClicked = { navController.navigateToMovieDetail(it) },
                                animatedContentScope = this@composable
                            )
                        }
                        composable(SEARCH_TV) {
                            TvSearchScreen(
                                viewModel = hiltViewModel<TvSearchViewModel>(),
                                onBackButtonClicked = { navController.navigateUp() },
                                onItemClicked = { navController.navigateToTvDetail(it) },
                                animatedContentScope = this@composable
                            )
                        }
                    }
                }
            }
        }
    }
}


