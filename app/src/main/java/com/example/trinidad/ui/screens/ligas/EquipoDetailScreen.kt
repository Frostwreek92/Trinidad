package com.example.trinidad.ui.screens.ligas


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trinidad.ui.components.ExpandableSection
import com.example.trinidad.ui.components.JugadorItem
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.example.trinidad.R

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

                // 🛡 ESCUDO DEL EQUIPO
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(team.logo)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Escudo del ${team.name}",
                    placeholder = painterResource(R.drawable.ic_player_placeholder),
                    error = painterResource(R.drawable.ic_player_placeholder),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(160.dp)
                        .padding(bottom = 16.dp)
                        .fillMaxSize()
                )

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
