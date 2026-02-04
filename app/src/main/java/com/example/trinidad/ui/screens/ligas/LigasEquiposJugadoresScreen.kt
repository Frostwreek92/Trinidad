package com.example.trinidad.ui.screens.ligas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trinidad.ui.components.LigaCard
import com.example.trinidad.ui.components.SearchBar

@Composable
fun LigasEquiposJugadoresScreen(
    onEquipoClick: (Int) -> Unit,
    viewModel: LigasViewModel = viewModel(factory = LigasViewModelFactory())
) {

    val ligasState by viewModel.ligasState.collectAsState()
    var searchQuery by rememberSaveable { mutableStateOf("") }

    when (ligasState) {

        is LigasUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is LigasUiState.Error -> {
            val message = (ligasState as LigasUiState.Error).message
            Text(
                text = message,
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.error
            )
        }

        is LigasUiState.Success -> {

            val ligas = (ligasState as LigasUiState.Success).ligas
                .filter {
                    it.name.contains(searchQuery, ignoreCase = true)
                }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    placeholder = "Buscar liga..."
                )

                Spacer(modifier = Modifier.height(16.dp))

                val ligasState by viewModel.ligasState.collectAsState()
                val teamsState by viewModel.teamsState.collectAsState()

                when (ligasState) {

                    is LigasUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is LigasUiState.Error -> {
                        Text(
                            text = (ligasState as LigasUiState.Error).message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    is LigasUiState.Success -> {
                        val ligas = (ligasState as LigasUiState.Success).ligas

                        LazyColumn {
                            items(ligas) { liga ->
                                LigaCard(
                                    liga = liga,
                                    teamsState = teamsState[liga.id] ?: TeamsUiState.Idle,
                                    onExpand = {
                                        viewModel.loadTeamsIfNeeded(liga.id)
                                    },
                                    onEquipoClick = onEquipoClick
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}