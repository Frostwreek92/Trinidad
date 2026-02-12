package com.example.trinidad.ui.screens.equipoLegendario

import android.content.ClipData
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.trinidad.domain.model.api.Player
import com.example.trinidad.domain.model.local.PosicionFormacion
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sqrt

// Función de extensión para calcular distancia entre dos puntos
private fun Offset.getDistance(): Float {
    return sqrt(x * x + y * y)
}

// Función para calcular distancia entre dos puntos
private fun Offset.distanceTo(other: Offset): Float {
    val dx = x - other.x
    val dy = y - other.y
    return sqrt(dx * dx + dy * dy)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormacionScreen(
    isEditing: Boolean = false,
    onSaveComplete: () -> Unit,
    viewModel: FormacionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var draggedPlayer by remember { mutableStateOf<Player?>(null) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var isDragging by remember { mutableStateOf(false) }
    val positionCoords = remember { mutableStateMapOf<Int, Offset>() }
    val positionBounds = remember { mutableStateMapOf<Int, Rect>() }


    LaunchedEffect(Unit) {
        if (isEditing) {
            viewModel.loadFormacion()
        } else {
            viewModel.initializeFormacion()
        }
    }
    
    LaunchedEffect(uiState.saveComplete) {
        if (uiState.saveComplete) {
            onSaveComplete()
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            
            Text(
                text = if (isEditing) "Modificar Formación" else "Nueva Formación",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            Row {
                Button(
                    onClick = { viewModel.clearAllPositions() },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Limpiar")
                }
                Button(
                    onClick = { viewModel.saveFormacion() },
                    enabled = uiState.isValidFormation()
                ) {
                    Text("Guardar")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Campo de fútbol con formación
            Text(
                text = "Formación 4-3-3",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp), // Increased height for larger vertical spacing
                contentAlignment = Alignment.Center
            ) {
                FootballFieldFormation(
                    positions = uiState.positions,
                    positionCoords = positionCoords,
                    positionBounds = positionBounds,
                    onPositionClick = { position ->
                        // Click normal para otras funciones
                    },
                    viewModel = viewModel,
                    draggedPlayer = draggedPlayer,
                    onDraggedPlayerChange = { draggedPlayer = it },
                    isDragging = isDragging,
                    onDraggingChange = { isDragging = it }
                )
            }
            
            // Lista de jugadores disponibles
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = "Jugadores Disponibles",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (uiState.availablePlayers.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay jugadores disponibles",
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.availablePlayers) { player ->
                                PlayerCard(
                                    player = player,
                                    onPlayerClick = { viewModel.selectPlayer(player) },
                                    onPlayerDragStart = { player, offset ->
                                        draggedPlayer = player
                                        // Iniciar desde el punto exacto de pulsación
                                        dragOffset = offset
                                        isDragging = true
                                    },
                                    onDrag = { dragAmount ->
                                        // Acumular los cambios de posición desde el punto inicial
                                        dragOffset = dragOffset + dragAmount
                                    },
                                    onDragEnd = {
                                        draggedPlayer?.let { player ->
                                            // Usar directamente las coordenadas de pantalla
                                            val dropPoint = dragOffset
                                            
                                            // Encontrar la posición cuyo bounds contiene el punto de drop
                                            val targetPosition = positionBounds.entries.firstOrNull { entry ->
                                                entry.value.contains(dropPoint)
                                            }?.key?.let { positionId ->
                                                uiState.positions.find { it.id == positionId }
                                            }
                                            
                                            // Si no está dentro de ningún bounds, usar el más cercano considerando ambos ejes
                                            val finalPosition = targetPosition ?: positionCoords.entries.minByOrNull { entry ->
                                                val distance = dropPoint.distanceTo(entry.value)
                                                // Calcular peso basado en la proximidad horizontal y vertical
                                                val horizontalWeight = abs(dropPoint.x - entry.value.x)
                                                val verticalWeight = abs(dropPoint.y - entry.value.y)
                                                // Dar más peso a la coincidencia en el mismo carril horizontal
                                                val laneBonus = if (horizontalWeight < 50f) 0.7f else 1.0f
                                                distance * laneBonus
                                            }?.let { entry ->
                                                uiState.positions.find { it.id == entry.key }
                                            }
                                            
                                            finalPosition?.let { position ->
                                                viewModel.assignPlayerToPosition(player, position)
                                            }
                                        }

                                        isDragging = false
                                        draggedPlayer = null
                                        dragOffset = Offset.Zero
                                    }

                                )
                            }
                        }
                    }
                }
            }
        }
    }
    
    // Overlay visual para el drag and drop - dentro del Box principal
    if (isDragging) {
        draggedPlayer?.let { player ->
            Box(
                modifier = Modifier
                    .offset { IntOffset(dragOffset.x.roundToInt(), dragOffset.y.roundToInt()) }
                    .size(100.dp, 60.dp) // Grande y visible
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = player.name.take(10), // Más caracteres visibles
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = player.position,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
    } // Cierre del Box principal
    
    // Mensajes de estado
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

@Composable
fun FootballFieldFormation(
    positions: List<PosicionFormacion>,
    onPositionClick: (PosicionFormacion) -> Unit,
    viewModel: FormacionViewModel,
    draggedPlayer: Player?,
    onDraggedPlayerChange: (Player?) -> Unit,
    isDragging: Boolean,
    onDraggingChange: (Boolean) -> Unit,
    positionCoords: MutableMap<Int, Offset>,
    positionBounds: MutableMap<Int, Rect>
) {
    // Real coordinates will be captured by onGloballyPositioned in PositionMarker
    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1.2f) // Más alto para acomodar todas las posiciones verticales
            .background(
                color = Color.Green.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 2.dp,
                color = Color.Green,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp) // Increased padding for better spacing
    ) {
        // Líneas del campo
        // Línea central
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
                .background(Color.White)
                .align(Alignment.Center)
        )
        
        // Círculo central
        Box(
            modifier = Modifier
                .size(40.dp) // Más pequeño
                .background(
                    color = Color.Transparent,
                    shape = CircleShape
                )
                .border(
                    width = 1.dp, // Más delgado
                    color = Color.White,
                    shape = CircleShape
                )
                .align(Alignment.Center)
        )
        
        // Posiciones de los jugadores
        positions.forEach { position ->
            PositionMarker(
                position = position,
                onClick = { onPositionClick(position) },
                modifier = Modifier
                    .offset(
                        x = (position.x * 420).dp - 20.dp, // Increased horizontal spacing
                        y = (position.y * 300).dp - 20.dp  // Increased vertical spacing
                    ),
                draggedPlayer = draggedPlayer,
                onPlayerDrop = { player ->
                    viewModel.assignPlayerToPosition(player, position)
                    onDraggedPlayerChange(null)
                    onDraggingChange(false)
                },
                isDragging = isDragging,
                onPositionLayout = { pos, offset ->
                    positionCoords[pos.id] = offset
                },
                onPositionBounds = { pos, bounds ->
                    positionBounds[pos.id] = bounds
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PositionMarker(
    position: PosicionFormacion,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    draggedPlayer: Player?,
    onPlayerDrop: (Player) -> Unit = {},
    onPositionLayout: (PosicionFormacion, Offset) -> Unit = { _, _ -> },
    onPositionBounds: (PosicionFormacion, Rect) -> Unit = { _, _ -> },
    isDragging: Boolean = false
) {
    var isDragOver by remember { mutableStateOf(false) }
    
    Card(
        modifier = modifier
            .size(40.dp) // Increased size for better visibility in horizontal layout
            .onGloballyPositioned { coords ->
                // Usar coordenadas absolutas de pantalla en lugar de relativas al campo
                val screenPosition = coords.positionInRoot()
                val bounds = coords.boundsInRoot()
                onPositionLayout(position, screenPosition)
                onPositionBounds(position, bounds)
            }
            .let { mod ->
                if (position.jugador != null) {
                    mod.dragAndDropSource {
                        startTransfer(
                            DragAndDropTransferData(
                                ClipData.newPlainText(
                                    "player",
                                    "${position.jugador!!.id}|${position.jugador!!.name}"
                                )
                            )
                        )
                    }
                } else {
                    mod
                }
            }
            .dragAndDropTarget(
                shouldStartDragAndDrop = { true },
                target = remember {
                    object : DragAndDropTarget {
                        override fun onEntered(event: DragAndDropEvent) {
                            isDragOver = true
                        }
                        
                        override fun onExited(event: DragAndDropEvent) {
                            isDragOver = false
                        }
                        
                        override fun onDrop(event: DragAndDropEvent): Boolean {
                            isDragOver = false
                            draggedPlayer?.let { player ->
                                onPlayerDrop(player)
                            }
                            return true
                        }
                    }
                }
            ),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = when {
                isDragOver -> Color.Yellow.copy(alpha = 0.7f)
                position.jugador != null -> Color.Blue
                else -> Color.Gray.copy(alpha = 0.5f)
            }
        ),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (position.jugador != null) {
                    Text(
                        text = position.jugador!!.name.take(2).uppercase(), // Player initials when occupied
                        color = Color.White,
                        fontSize = 9.sp, // Increased font size for better readability
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = position.posicion, // Position abbreviation when empty
                        color = Color.White,
                        fontSize = 9.sp, // Increased font size for better readability
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PlayerCard(
    player: Player,
    onPlayerClick: () -> Unit,
    onPlayerDragStart: (Player, Offset) -> Unit = { _, _ -> },
    onDrag: (Offset) -> Unit = { _ -> },
    onDragEnd: () -> Unit = {}
) {
    var cardPosition by remember { mutableStateOf(Offset.Zero) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .dragAndDropSource {
                startTransfer(
                    DragAndDropTransferData(
                        ClipData.newPlainText(
                            "player",
                            "${player.id}|${player.name}"
                        )
                    )
                )
            }
            .onGloballyPositioned { coords ->
                cardPosition = coords.positionInRoot()
            }
            .pointerInput(player) {
                detectDragGestures(
                    onDragStart = { offset -> 
                        // Convertir coordenadas locales a coordenadas de pantalla
                        val screenOffset = cardPosition + offset
                        onPlayerDragStart(player, screenOffset)
                    },
                    onDragEnd = {
                        onDragEnd()
                    }
                ) { change, dragAmount ->
                    change.consume()
                    onDrag(dragAmount)
                }
            },
        onClick = onPlayerClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Foto del jugador (placeholder)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = player.name.first().toString(),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = player.name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
                Text(
                    text = player.position,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
