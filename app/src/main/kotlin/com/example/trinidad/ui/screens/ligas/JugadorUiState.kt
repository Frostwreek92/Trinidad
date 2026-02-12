package com.example.trinidad.ui.screens.ligas

import com.example.trinidad.domain.model.api.Player

sealed class JugadorUiState {
    object Idle : JugadorUiState()
    object Loading : JugadorUiState()
    data class Success(val players: List<Player>) : JugadorUiState()
    data class Error(val message: String) : JugadorUiState()
}
