package com.example.trinidad.equipoLegendario.domain.model

import com.example.trinidad.domain.model.Player

data class PosicionFormacion(
    val id: Int,
    val posicion: String,
    val x: Float,
    val y: Float,
    val jugador: Player? = null
)
