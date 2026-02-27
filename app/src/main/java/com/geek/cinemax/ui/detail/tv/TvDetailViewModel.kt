package com.geek.cinemax.ui.detail.tv

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geek.cinemax.data.repository.TvRepository
import com.geek.cinemax.ui.model.toContent
import com.geek.cinemax.ui.state.ContentDetailUiState
import com.geek.cinemax.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TvRepository,
) : ViewModel() {
    private val remoteId: Long = requireNotNull(savedStateHandle[AppConstants.ROUTE_DETAIL_ARG_NAME])

    // This is a mutable state flow that will be used internally in the viewmodel,
    private val _tvUiState = MutableStateFlow<ContentDetailUiState>(ContentDetailUiState.Loading)

    // Immutable state flow that is exposed to the UI
    val tvUiState = _tvUiState.asStateFlow()

    init { getTv() }

    fun refresh() = getTv()

    private fun getTv() = viewModelScope.launch {
        _tvUiState.update {
            try {
                val tv = repository.getTv(remoteId)
                ContentDetailUiState.Success(tv.toContent())
            } catch (e: Exception) { ContentDetailUiState.Error }
        }
    }
}