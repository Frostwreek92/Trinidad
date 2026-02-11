package com.example.trinidad.equipoLegendario.ui.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trinidad.equipoLegendario.data.repository.EquipoLegendarioRepositoryImpl
import com.example.trinidad.equipoLegendario.domain.model.Formacion
import com.example.trinidad.equipoLegendario.domain.model.JugadorEnPosicion
import com.example.trinidad.domain.model.Player
import com.example.trinidad.equipoLegendario.domain.model.PosicionFormacion
import com.example.trinidad.equipoLegendario.domain.usecase.GetAllJugadoresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class FormacionViewModel @Inject constructor(
    private val getAllJugadoresUseCase: GetAllJugadoresUseCase,
    private val repositoryImpl: EquipoLegendarioRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormacionUiState())
    val uiState: StateFlow<FormacionUiState> = _uiState.asStateFlow()

    fun initializeFormacion() {
        viewModelScope.launch {
            try {
                val jugadores = getAllJugadoresUseCase()
                val positions = repositoryImpl.getDefaultFormationPositions()
                
                _uiState.value = _uiState.value.copy(
                    positions = positions,
                    availablePlayers = jugadores,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    message = "Error al cargar los jugadores: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun loadFormacion() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                
                // Cargar formación desde Spring Boot
                val formacion = repositoryImpl.getFormacion()
                val jugadores = getAllJugadoresUseCase()
                
                if (formacion != null) {
                    val positions = repositoryImpl.getDefaultFormationPositions().map { defaultPos ->
                        val jugadorEnPosicion = formacion.jugadores.find { 
                            it.posicion == defaultPos.posicion 
                        }
                        defaultPos.copy(jugador = jugadorEnPosicion?.jugador)
                    }
                    
                    _uiState.value = _uiState.value.copy(
                        positions = positions,
                        availablePlayers = jugadores.filter { player ->
                            !formacion.jugadores.any { it.jugador.id == player.id }
                        },
                        currentFormacion = formacion,
                        isLoading = false
                    )
                } else {
                    initializeFormacion()
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    message = "Error al cargar la formación: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun selectPlayer(player: Player) {
        val currentPosition = _uiState.value.selectedPosition
        if (currentPosition != null) {
            val updatedPositions = _uiState.value.positions.map { position ->
                if (position.posicion == currentPosition.posicion) {
                    position.copy(jugador = player)
                } else {
                    position
                }
            }
            
            val updatedAvailablePlayers = _uiState.value.availablePlayers.filter { it.id != player.id }
            
            _uiState.value = _uiState.value.copy(
                positions = updatedPositions,
                availablePlayers = updatedAvailablePlayers,
                selectedPosition = null
            )
        }
    }

    fun selectPosition(position: PosicionFormacion) {
        _uiState.value = _uiState.value.copy(selectedPosition = position)
    }

    fun saveFormacion() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                
                val jugadoresEnPosicion = _uiState.value.positions
                    .filter { it.jugador != null }
                    .map { position ->
                        JugadorEnPosicion(
                            jugador = position.jugador!!,
                            posicion = position.posicion,
                            x = position.x,
                            y = position.y
                        )
                    }
                
                val formacion = Formacion(
                    id = _uiState.value.currentFormacion?.id,
                    esquema = "4-3-3",
                    jugadores = jugadoresEnPosicion
                )
                
                // Guardar únicamente en Spring Boot
                repositoryImpl.saveFormacion(formacion)
                
                _uiState.value = _uiState.value.copy(
                    saveComplete = true,
                    message = "Formación guardada correctamente en servidor",
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    message = "Error al guardar la formación: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun assignPlayerToPosition(player: Player, position: PosicionFormacion) {
        val updatedPositions = _uiState.value.positions.map { pos ->
            if (pos.posicion == position.posicion) {
                // If position is occupied, return the previous player to available players
                pos.jugador?.let { previousPlayer ->
                    val currentAvailablePlayers = _uiState.value.availablePlayers.toMutableList()
                    if (!currentAvailablePlayers.any { it.id == previousPlayer.id }) {
                        currentAvailablePlayers.add(previousPlayer)
                    }
                    _uiState.value = _uiState.value.copy(availablePlayers = currentAvailablePlayers)
                }
                pos.copy(jugador = player)
            } else {
                pos
            }
        }
        
        val updatedAvailablePlayers = _uiState.value.availablePlayers.filter { it.id != player.id }
        
        _uiState.value = _uiState.value.copy(
            positions = updatedPositions,
            availablePlayers = updatedAvailablePlayers
        )
    }

    fun removePlayerFromPosition(playerId: Int) {
        val updatedPositions = _uiState.value.positions.map { pos ->
            if (pos.jugador?.id == playerId) {
                // Add the removed player back to available players
                val currentAvailablePlayers = _uiState.value.availablePlayers.toMutableList()
                if (!currentAvailablePlayers.any { it.id == playerId }) {
                    currentAvailablePlayers.add(pos.jugador!!)
                }
                _uiState.value = _uiState.value.copy(availablePlayers = currentAvailablePlayers)
                pos.copy(jugador = null)
            } else {
                pos
            }
        }
        
        _uiState.value = _uiState.value.copy(positions = updatedPositions)
    }

    fun clearAllPositions() {
        val updatedPositions = _uiState.value.positions.map { position ->
            position.copy(jugador = null)
        }
        
        // Add all positioned players back to available players
        val positionedPlayers = _uiState.value.positions.mapNotNull { it.jugador }
        val currentAvailablePlayers = _uiState.value.availablePlayers
        val allAvailablePlayers = (currentAvailablePlayers + positionedPlayers).distinctBy { it.id }
        
        _uiState.value = _uiState.value.copy(
            positions = updatedPositions,
            availablePlayers = allAvailablePlayers
        )
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
}

data class FormacionUiState(
    val positions: List<PosicionFormacion> = emptyList(),
    val availablePlayers: List<Player> = emptyList(),
    val currentFormacion: Formacion? = null,
    val selectedPosition: PosicionFormacion? = null,
    val isLoading: Boolean = true,
    val saveComplete: Boolean = false,
    val message: String? = null
) {
    fun isValidFormation(): Boolean {
        return positions.count { it.jugador != null } == 11
    }
}
