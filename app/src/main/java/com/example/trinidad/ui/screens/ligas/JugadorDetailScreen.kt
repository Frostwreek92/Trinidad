package com.example.trinidad.ui.screens.ligas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trinidad.R

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
