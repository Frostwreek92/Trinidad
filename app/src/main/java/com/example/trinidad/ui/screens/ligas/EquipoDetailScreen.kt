package com.example.trinidad.ui.screens.ligas


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trinidad.ui.components.ExpandableSection
import com.example.trinidad.ui.components.JugadorItem

@Composable
fun EquipoDetailScreen(
    equipoId: Int,
    onJugadorClick: (Int) -> Unit
) {

    val viewModel: EquipoDetailViewModel =
        viewModel(factory = EquipoDetailViewModelFactory(equipoId))

    // Estado del equipo
    val teamState by viewModel.teamState.collectAsState()

    // Estado de los jugadores
    val playersState by viewModel.playersState.collectAsState()

    when (teamState) {

        is EquipoDetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is EquipoDetailUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (teamState as EquipoDetailUiState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        is EquipoDetailUiState.Success -> {

            val team = (teamState as EquipoDetailUiState.Success).team

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                // Nombre del equipo
                Text(
                    text = team.name,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(Modifier.height(8.dp))

                // Información del equipo
                Text("Estadio: ${team.stadium}")
                Text("Ciudad: ${team.city}")
                Text("Capacidad: ${team.capacity}")

                Spacer(Modifier.height(24.dp))

                // 🔽 SECCIÓN DESPLEGABLE DE JUGADORES
                ExpandableSection(
                    title = "Jugadores",
                    onExpand = {
                        viewModel.loadPlayers()
                    }
                ) {

                    when (playersState) {

                        is PlayersUiState.Idle -> {
                            Text(
                                text = "Pulsa para cargar jugadores",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        is PlayersUiState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        is PlayersUiState.Error -> {
                            Text(
                                text = (playersState as PlayersUiState.Error).message,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        is PlayersUiState.Success -> {
                            val players =
                                (playersState as PlayersUiState.Success).players

                            players.forEach { player ->
                                JugadorItem(
                                    nombre = player.name,
                                    posicion = player.position,
                                    onClick = {
                                        onJugadorClick(player.id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
