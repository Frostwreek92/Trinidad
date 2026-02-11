package com.example.trinidad.ui.screens.ligas

import com.example.trinidad.domain.model.api.Team

sealed class EquipoUiState {
    object Idle : EquipoUiState()
    object Loading : EquipoUiState()
    data class Success(val teams: List<Team>) : EquipoUiState()
    data class Error(val message: String) : EquipoUiState()
}
