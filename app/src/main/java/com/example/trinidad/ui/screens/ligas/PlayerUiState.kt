package com.example.trinidad.ui.screens.ligas

import com.example.trinidad.domain.model.Player

sealed class PlayersUiState {
    object Idle : PlayersUiState()
    object Loading : PlayersUiState()
    data class Success(val players: List<Player>) : PlayersUiState()
    data class Error(val message: String) : PlayersUiState()
}
