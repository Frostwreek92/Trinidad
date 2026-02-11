package com.example.trinidad.equipoLegendario.domain.model

import com.example.trinidad.domain.model.Player

data class Formacion(
    val id: Int? = null,
    val esquema: String = "4-3-3",
    val jugadores: List<JugadorEnPosicion> = emptyList()
)

data class JugadorEnPosicion(
    val jugador: Player,
    val posicion: String, // "PT", "LI", "LD", "DFC", "MCD", "MC", "MI", "MD", "SD", "EI", "ED"
    val x: Float,
    val y: Float
)
