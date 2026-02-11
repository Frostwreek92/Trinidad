package com.example.trinidad.ui.screens.ligas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trinidad.ui.components.LigaCard
import com.example.trinidad.ui.components.SearchBar

@Composable
fun LigasDetailScreen(
    onEquipoClick: (Int) -> Unit,
    viewModel: LigasViewModel = viewModel(factory = LigasViewModelFactory())
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
            },
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
                val allLigas = (ligasState as LigasUiState.Success).ligas
                val filteredLigas = if (searchQuery.isBlank()) {
                    allLigas
                } else {
                    allLigas.filter { liga ->
                        liga.name.contains(searchQuery, ignoreCase = true) ||
                                liga.country.contains(searchQuery, ignoreCase = true)
                    }
                }
                if (filteredLigas.isEmpty()) {
                    Text(
                        text = "No se encontraron resultados",
                        color = MaterialTheme.colorScheme.error
                    )
                } else {
                    LazyColumn (
                        modifier = Modifier
                    ) {
                        items(filteredLigas) { liga ->
                            LigaCard(
                                liga = liga,
                                teamsState = teamsState[liga.id] ?: EquipoUiState.Idle,
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