package com.example.trinidad.ui.screens.equipoLegendario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipoLegendarioScreen(
    onNavigateToFormacionCreator: () -> Unit,
    onNavigateToFormacionViewer: () -> Unit,
    onNavigateToFormacionEditor: () -> Unit,
    viewModel: EquipoLegendarioViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        viewModel.checkFormacionExists()
        viewModel.loadJugadores()
    }
    
    // Diálogo de confirmación para eliminar
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "Confirmar Eliminación",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text("¿Estás seguro de que quieres eliminar la formación legendaria?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteFormacion()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Aceptar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Equipo Legendario",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 32.dp)
        )
        
        Text(
            text = "Crea tu equipo soñado con los mejores jugadores",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Botón Nuevo
        Button(
            onClick = {
                if (!uiState.hasFormacion) {
                    onNavigateToFormacionCreator()
                } else {
                    viewModel.showCannotCreateMessage()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Nuevo",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Botón Ver Formación
        Button(
            onClick = {
                if (uiState.hasFormacion) {
                    onNavigateToFormacionViewer()
                } else {
                    viewModel.showCannotViewMessage()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text(
                text = "Ver Formación",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Botón Modificar
        Button(
            onClick = {
                if (uiState.hasFormacion) {
                    onNavigateToFormacionEditor()
                } else {
                    viewModel.showCannotModifyMessage()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                text = "Modificar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        // Botón Eliminar
        Button(
            onClick = {
                if (uiState.hasFormacion) {
                    showDeleteDialog = true
                } else {
                    viewModel.showCannotDeleteMessage()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {
            Text(
                text = "Eliminar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
        
        // Estado actual
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Estado Actual",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (uiState.hasFormacion) {
                        "Tienes una formación guardada"
                    } else {
                        "No tienes ninguna formación"
                    },
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        // LazyRow de Jugadores
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Jugadores Disponibles",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                when {
                    uiState.isLoadingJugadores -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    
                    uiState.jugadores.isEmpty() -> {
                        Text(
                            text = "No hay jugadores disponibles",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    
                    else -> {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(uiState.jugadores) { jugador ->
                                Card(
                                    modifier = Modifier
                                        .width(80.dp)
                                        .height(120.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        AsyncImage(
                                            model = jugador.photo,
                                            contentDescription = jugador.name,
                                            modifier = Modifier
                                                .size(40.dp)
                                                .padding(bottom = 4.dp)
                                        )
                                        Text(
                                            text = jugador.name,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Medium,
                                            textAlign = TextAlign.Center,
                                            maxLines = 2
                                        )
                                        Text(
                                            text = jugador.position,
                                            fontSize = 8.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    // Snackbar para mensajes
    uiState.message?.let { message ->
        LaunchedEffect(message) {
            delay(3000) // Esperar 3 segundos antes de limpiar el mensaje
            viewModel.clearMessage()
        }
        
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                TextButton(onClick = { viewModel.clearMessage() }) {
                    Text("OK")
                }
            }
        ) {
            Text(message)
        }
    }
}
