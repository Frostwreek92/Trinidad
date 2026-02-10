package com.example.trinidad.ui.components

import EquipoItem
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.trinidad.domain.model.League
import com.example.trinidad.ui.screens.ligas.EquipoUiState


@Composable
fun LigaCard(
    liga: League,
    teamsState: EquipoUiState,
    onExpand: () -> Unit,
    onEquipoClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
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
}
