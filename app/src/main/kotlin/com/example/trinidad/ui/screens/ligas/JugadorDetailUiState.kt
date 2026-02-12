package com.example.trinidad.ui.screens.ligas

import com.example.trinidad.domain.model.api.PlayerDetail

sealed class JugadorDetailUiState {
    object Loading : JugadorDetailUiState()
    data class Success(val player: PlayerDetail) : JugadorDetailUiState()
    data class Error(val message: String) : JugadorDetailUiState()
}
