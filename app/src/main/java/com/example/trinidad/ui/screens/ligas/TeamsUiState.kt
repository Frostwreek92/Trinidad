package com.example.trinidad.ui.screens.ligas

import com.example.trinidad.domain.model.Team

sealed class TeamsUiState {
    object Idle : TeamsUiState()
    object Loading : TeamsUiState()
    data class Success(val teams: List<Team>) : TeamsUiState()
    data class Error(val message: String) : TeamsUiState()
}
