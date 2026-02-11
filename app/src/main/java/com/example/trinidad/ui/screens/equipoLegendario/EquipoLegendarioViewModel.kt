package com.example.trinidad.ui.screens.equipoLegendario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trinidad.data.mapper.local.BackendJugadorMapper
import com.example.trinidad.data.remote.local.EquipoLegendarioApi
import com.example.trinidad.domain.model.api.Player
import com.example.trinidad.data.repository.EquipoLegendarioRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EquipoLegendarioViewModel @Inject constructor(
    private val repositoryImpl: EquipoLegendarioRepositoryImpl,
    private val equipoLegendarioApi: EquipoLegendarioApi,
    private val backendJugadorMapper: BackendJugadorMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(EquipoLegendarioUiState())
    val uiState: StateFlow<EquipoLegendarioUiState> = _uiState.asStateFlow()

    fun checkFormacionExists() {
        viewModelScope.launch {
            try {
                val exists = repositoryImpl.existsFormacion()
                _uiState.value = _uiState.value.copy(hasFormacion = exists)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(hasFormacion = false)
            }
        }
    }

    fun loadJugadores() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingJugadores = true)
            try {
                println("Cargando jugadores desde API del backend...")
                val jugadoresDto = equipoLegendarioApi.getJugadoresFromBackend()
                println("Jugadores DTO recibidos: ${jugadoresDto.size}")
                jugadoresDto.forEach { jugador ->
                    println("Jugador DTO: ${jugador.nombreJugador} - ${jugador.posicion}")
                }
                val jugadores = backendJugadorMapper.mapToDomain(jugadoresDto)
                println("Jugadores mapeados: ${jugadores.size}")
                jugadores.forEach { jugador ->
                    println("Jugador: ${jugador.name} - ${jugador.position}")
                }
                _uiState.value = _uiState.value.copy(
                    jugadores = jugadores,
                    isLoadingJugadores = false
                )
            } catch (e: Exception) {
                println("Error cargando jugadores: ${e.message}")
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(
                    jugadores = emptyList(),
                    isLoadingJugadores = false
                )
            }
        }
    }

    fun deleteFormacion() {
        viewModelScope.launch {
            try {
                val success = repositoryImpl.deleteFormacion()
                if (success) {
                    _uiState.value = _uiState.value.copy(
                        hasFormacion = false,
                        message = "Formación eliminada correctamente del servidor"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        message = "Error al eliminar la formación"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    message = "Error al eliminar la formación: ${e.message}"
                )
            }
        }
    }

    fun showCannotCreateMessage() {
        _uiState.value = _uiState.value.copy(
            message = "No se puede crear una nueva formación. Ya tienes una formación guardada (límite: 1)"
        )
    }

    fun showCannotModifyMessage() {
        _uiState.value = _uiState.value.copy(
            message = "No se puede modificar. No tienes ninguna formación guardada"
        )
    }

    fun showCannotDeleteMessage() {
        _uiState.value = _uiState.value.copy(
            message = "No se puede eliminar. No tienes ninguna formación guardada"
        )
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
}

data class EquipoLegendarioUiState(
    val hasFormacion: Boolean = false,
    val message: String? = null,
    val jugadores: List<Player> = emptyList(),
    val isLoadingJugadores: Boolean = true
)
