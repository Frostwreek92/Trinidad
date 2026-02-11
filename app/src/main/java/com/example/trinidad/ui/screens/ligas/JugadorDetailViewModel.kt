package com.example.trinidad.ui.screens.ligas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trinidad.domain.usecase.api.GetPlayerDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JugadorDetailViewModel(
    private val playerId: Int,
    private val getPlayerDetailUseCase: GetPlayerDetailUseCase
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<JugadorDetailUiState>(JugadorDetailUiState.Loading)
    val uiState: StateFlow<JugadorDetailUiState> = _uiState
    init {
        loadPlayer()
    }
    private fun loadPlayer() {
        viewModelScope.launch {
            try {
                val player = getPlayerDetailUseCase(playerId)
                _uiState.value = JugadorDetailUiState.Success(player)
            } catch (e: Exception) {
                _uiState.value =
                    JugadorDetailUiState.Error("Error cargando jugador")
            }
        }
    }
}
