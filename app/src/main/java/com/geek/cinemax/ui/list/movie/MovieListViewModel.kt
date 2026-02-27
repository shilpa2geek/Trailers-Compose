package com.geek.cinemax.ui.list.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.geek.cinemax.AppConstants
import com.geek.cinemax.data.local.CategoryStore
import com.geek.cinemax.data.remote.model.Category
import com.geek.cinemax.data.repository.MovieRepository
import com.geek.cinemax.data.source.MovieRemoteMediator
import com.geek.cinemax.ui.model.toContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val categoryStore: CategoryStore
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow(Category.POPULAR)

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies = _selectedCategory.flatMapLatest {
        getPager(it)
            .flow
            .cachedIn(viewModelScope)
            .map { pagingData ->
                pagingData.map { movie -> movie.toContent() }
            }
    }

    val selectedCategory = _selectedCategory.asStateFlow()
    fun updateCategory(newCategory: Category) {
        _selectedCategory.update { newCategory }
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun getPager(category: Category) = Pager(
        config = PagingConfig(
            pageSize = AppConstants.PAGE_SIZE,
            prefetchDistance = AppConstants.PREFETCH_DISTANCE,
            initialLoadSize = AppConstants.PAGE_SIZE, // How many items you want to load initially
        ),
        pagingSourceFactory = {
            // The pagingSourceFactory lambda should always return a brand new PagingSource
            // when invoked as PagingSource instances are not reusable.
            repository.getMoviesPagingSource()
        },
        remoteMediator = MovieRemoteMediator(
            category = category,
            categoryStore = categoryStore,
            repository = repository
        )
    )
}