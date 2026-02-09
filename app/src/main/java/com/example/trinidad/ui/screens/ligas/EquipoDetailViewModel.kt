package com.example.trinidad.ui.screens.ligas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trinidad.domain.usecase.GetPlayersByTeamUseCase
import com.example.trinidad.domain.usecase.GetTeamDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class EquipoDetailViewModel(
    private val teamId: Int,
    private val getTeamDetailUseCase: GetTeamDetailUseCase,
    private val getPlayersByTeamUseCase: GetPlayersByTeamUseCase
) : ViewModel() {
    private val _teamState =
        MutableStateFlow<EquipoDetailUiState>(EquipoDetailUiState.Loading)
    val teamState: StateFlow<EquipoDetailUiState> = _teamState
    private val _playersState =
        MutableStateFlow<JugadorUiState>(JugadorUiState.Idle)
    val playersState: StateFlow<JugadorUiState> = _playersState
    init {
        loadTeam()
    }
    private fun loadTeam() {
        viewModelScope.launch {
            try {
                _teamState.value =
                    EquipoDetailUiState.Success(
                        getTeamDetailUseCase(teamId)
                    )
            } catch (e: Exception) {
                _teamState.value =
                    EquipoDetailUiState.Error("Error cargando equipo")
            }
        }
    }
    fun loadPlayers() {
        if (_playersState.value != JugadorUiState.Idle) return
        _playersState.value = JugadorUiState.Loading
        viewModelScope.launch {
            try {
                Log.d("PlayersAPI", "Cargando jugadores del equipo $teamId")
                val players = getPlayersByTeamUseCase(teamId)
                Log.d("PlayersAPI", "Jugadores recibidos: ${players.size}")
                if (players.isEmpty()) {
                    _playersState.value =
                        JugadorUiState.Error("No hay jugadores disponibles")
                } else {
                    _playersState.value =
                        JugadorUiState.Success(players)
                }
            } catch (e: HttpException) {
                Log.e(
                    "PlayersAPI",
                    "HTTP ${e.code()} – ${e.message()}",
                    e
                )
                _playersState.value =
                    JugadorUiState.Error("Error HTTP ${e.code()}")
            } catch (e: Exception) {
                Log.e(
                    "PlayersAPI",
                    "Error inesperado cargando jugadores",
                    e
                )
                _playersState.value =
                    JugadorUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

