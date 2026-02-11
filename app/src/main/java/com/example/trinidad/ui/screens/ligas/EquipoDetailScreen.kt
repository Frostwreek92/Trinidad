package com.example.trinidad.ui.screens.ligas


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.trinidad.R
import com.example.trinidad.ui.components.ExpandableSection
import com.example.trinidad.ui.components.JugadorItem
import com.example.trinidad.ui.theme.DarkGreen

@Composable
fun EquipoDetailScreen(
    equipoId: Int,
    onJugadorClick: (Int) -> Unit
) {
    val viewModel: EquipoDetailViewModel =
        viewModel(factory = EquipoDetailViewModelFactory(equipoId))
    val teamState by viewModel.teamState.collectAsState()
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
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier
                        .size(240.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(team.logo)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Escudo del ${team.name}",
                        placeholder = painterResource(R.drawable.ic_team_placeholder),
                        error = painterResource(R.drawable.ic_team_placeholder),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    )
                }
                Text(
                    text = team.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(Modifier.height(8.dp))
                Text("Estadio: ${team.stadium}")
                Text("Ciudad: ${team.city}")
                Text("Capacidad: ${team.capacity}")
                Spacer(Modifier.height(24.dp))
                ExpandableSection(
                    title = "Jugadores",
                    onExpand = {
                        viewModel.loadPlayers()
                    }
                ) {
                    when (playersState) {
                        is JugadorUiState.Idle -> {
                            Text(
                                text = "Pulsa para cargar jugadores",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        is JugadorUiState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        is JugadorUiState.Error -> {
                            Text(
                                text = (playersState as JugadorUiState.Error).message,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        is JugadorUiState.Success -> {
                            val players = (playersState as JugadorUiState.Success).players
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
