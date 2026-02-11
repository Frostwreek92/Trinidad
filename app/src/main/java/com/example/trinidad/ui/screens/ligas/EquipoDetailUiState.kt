package com.example.trinidad.ui.screens.ligas

import com.example.trinidad.domain.model.api.TeamDetail

sealed class EquipoDetailUiState {
    object Loading : EquipoDetailUiState()
    data class Success(val team: TeamDetail) : EquipoDetailUiState()
    data class Error(val message: String) : EquipoDetailUiState()
}
