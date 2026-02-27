package com.geek.cinemax.ui.state

import com.geek.cinemax.ui.model.Content

sealed class ContentDetailUiState {
    data class Success(val content: Content) : ContentDetailUiState()
    data object Error : ContentDetailUiState()
    data object Loading : ContentDetailUiState()
}
