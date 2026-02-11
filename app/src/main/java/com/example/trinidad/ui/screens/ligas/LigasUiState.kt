package com.example.trinidad.ui.screens.ligas

import com.example.trinidad.domain.model.api.League

sealed class LigasUiState {
    object Loading : LigasUiState()
    data class Success(val ligas: List<League>) : LigasUiState()
    data class Error(val message: String) : LigasUiState()
}