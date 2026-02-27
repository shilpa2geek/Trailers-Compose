package com.an.trailers_compose.ui.detail.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.an.trailers_compose.AppConstants.ROUTE_DETAIL_ARG_NAME
import com.an.trailers_compose.data.repository.MovieRepository
import com.an.trailers_compose.ui.model.toContent
import com.an.trailers_compose.ui.state.ContentDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: MovieRepository,
) : ViewModel() {
    private val remoteId: Long = requireNotNull(savedStateHandle[ROUTE_DETAIL_ARG_NAME])

    // This is a mutable state flow that will be used internally in the viewmodel,
    private val _movieUiState = MutableStateFlow<ContentDetailUiState>(ContentDetailUiState.Loading)

    // Immutable state flow that is exposed to the UI
    val movieUiState = _movieUiState.asStateFlow()

    init { getMovie() }

    fun refresh() = getMovie()

    private fun getMovie() = viewModelScope.launch {
        _movieUiState.update {
            try {
                val movie = repository.getMovie(remoteId)
                ContentDetailUiState.Success(movie.toContent())
            } catch (e: Exception) { ContentDetailUiState.Error }
        }
    }
}