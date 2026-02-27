package com.geek.cinemax.ui.list.movie

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.geek.cinemax.R
import com.geek.cinemax.data.remote.model.Category
import com.geek.cinemax.ui.list.common.CircleRevealPager
import com.geek.cinemax.ui.list.common.ContentCategories
import com.geek.cinemax.ui.list.common.ContentMenuBar
import com.geek.cinemax.ui.component.ErrorScreen
import com.geek.cinemax.ui.component.LoadingItem
import com.geek.cinemax.ui.model.Content
import com.geek.cinemax.AppConstants

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MovieListScreen(
    movies: LazyPagingItems<Content>,
    onItemClicked: (remoteId: Long) -> Unit,
    selectedCategory: Category,
    onCategorySelected: (category: Category) -> Unit,
    onTvMenuSelected: () -> Unit,
    onSearchSelected: () -> Unit,
    animatedContentScope: AnimatedContentScope
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Different load states – Loading, Empty State, Pager list state
        val loadState = movies.loadState.mediator
        when (loadState?.refresh) {
            is LoadState.Loading -> {
                LoadingItem()
            }
            is LoadState.Error -> {
                val error = (loadState.refresh as LoadState.Error).error
                ErrorScreen(errorMessage = error.message ?: error.toString()) {
                    movies.refresh()
                }
            }
            else -> {
                // Movie List
                CircleRevealPager(
                    contentList = movies,
                    onItemClicked = onItemClicked,
                    animatedContentScope = animatedContentScope
                )

                // Added filter, search & Tv option
                ContentMenuBar(
                    resId = R.drawable.ic_tv,
                    onMenuSelected = onTvMenuSelected,
                    onSearchSelected = onSearchSelected
                )

                // Filter by category
                ContentCategories(
                    selectedCategory = selectedCategory,
                    categories = AppConstants.movieCategories,
                    onCategorySelected = onCategorySelected
                )
            }
        }
    }
}
