package com.example.trinidad.ui.screens.ligas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trinidad.domain.usecase.GetLeaguesUseCase
import com.example.trinidad.domain.usecase.GetTeamsByLeagueUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LigasViewModel(
    private val getLeaguesUseCase: GetLeaguesUseCase,
    private val getTeamsByLeague: GetTeamsByLeagueUseCase
) : ViewModel() {
    private val _ligasState = MutableStateFlow<LigasUiState>(LigasUiState.Loading)
    val ligasState: StateFlow<LigasUiState> = _ligasState
    private val _teamsState =
        MutableStateFlow<Map<Int, EquipoUiState>>(emptyMap())
    val teamsState: StateFlow<Map<Int, EquipoUiState>> = _teamsState
    init {
        loadLigas()
    }
    private fun loadLigas() {
        viewModelScope.launch {
            try {
                _ligasState.value =
                    LigasUiState.Success(getLeaguesUseCase())
            } catch (e: Exception) {
                _ligasState.value =
                    LigasUiState.Error("Error cargando ligas")
            }
        }
    }
    fun loadTeamsIfNeeded(leagueId: Int) {
        if (_teamsState.value[leagueId] != null) return
        _teamsState.value =
            _teamsState.value + (leagueId to EquipoUiState.Loading)
        viewModelScope.launch {
            try {
                val teams = getTeamsByLeague(leagueId)
                _teamsState.value =
                    _teamsState.value + (leagueId to EquipoUiState.Success(teams))
            } catch (e: Exception) {
                _teamsState.value =
                    _teamsState.value + (leagueId to EquipoUiState.Error("Error cargando equipos"))
            }
        }
    }
}
