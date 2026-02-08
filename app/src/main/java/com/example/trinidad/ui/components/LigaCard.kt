package com.example.trinidad.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.trinidad.domain.model.League
import com.example.trinidad.ui.screens.ligas.EquipoUiState

@Composable
fun LigaCard(
    liga: League,
    teamsState: EquipoUiState,
    onExpand: () -> Unit,
    onEquipoClick: (Int) -> Unit
) {
    ExpandableSection(
        title = liga.name,
        onExpand = onExpand
    ) {
        when (teamsState) {

            is EquipoUiState.Loading -> {
                CircularProgressIndicator()
            }

            is EquipoUiState.Error -> {
                Text(
                    text = teamsState.message,
                    color = MaterialTheme.colorScheme.error
                )
            }

            is EquipoUiState.Success -> {
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
