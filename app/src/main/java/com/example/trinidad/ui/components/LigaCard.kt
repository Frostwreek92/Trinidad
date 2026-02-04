package com.example.trinidad.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trinidad.domain.model.League
import com.example.trinidad.ui.screens.ligas.TeamsUiState

@Composable
fun LigaCard(
    liga: League,
    teamsState: TeamsUiState,
    onExpand: () -> Unit,
    onEquipoClick: (Int) -> Unit
) {
    ExpandableSection(
        title = liga.name,
        onExpand = onExpand
    ) {
        when (teamsState) {

            is TeamsUiState.Loading -> {
                CircularProgressIndicator()
            }

            is TeamsUiState.Error -> {
                Text(
                    text = teamsState.message,
                    color = MaterialTheme.colorScheme.error
                )
            }

            is TeamsUiState.Success -> {
                teamsState.teams.forEach { team ->
                    EquipoItem(
                        nombre = team.name,
                        onClick = { onEquipoClick(team.id) }
                    )
                }
            }

            else -> Unit
        }
    }
}
