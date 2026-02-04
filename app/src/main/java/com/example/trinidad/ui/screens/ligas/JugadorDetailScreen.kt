package com.example.trinidad.ui.screens.ligas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun JugadorDetailScreen(
    jugadorId: Int
) {

    val viewModel: JugadorDetailViewModel =
        viewModel(factory = JugadorDetailViewModelFactory(jugadorId))

    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {

        is JugadorDetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is JugadorDetailUiState.Error -> {
            Text(
                text = (uiState as JugadorDetailUiState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
        }

        is JugadorDetailUiState.Success -> {

            val player =
                (uiState as JugadorDetailUiState.Success).player

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // (Más adelante aquí pondrás Coil)
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(Modifier.height(12.dp))

                Text("Posición: ${player.position}")
                Text("Edad: ${player.age}")
                Text("Nacionalidad: ${player.nationality}")
            }
        }
    }
}
