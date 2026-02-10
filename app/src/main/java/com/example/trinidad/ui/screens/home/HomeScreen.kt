package com.example.trinidad.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNavigateToLigas: () -> Unit,
    onNavigateToEquipoLegendario: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onNavigateToLigas,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Ligas / Equipos / Jugadores")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onNavigateToEquipoLegendario,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Creador Equipo Legendario")
        }
    }
}