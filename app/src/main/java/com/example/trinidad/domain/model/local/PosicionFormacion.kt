package com.example.trinidad.domain.model.local

import com.example.trinidad.domain.model.api.Player

data class PosicionFormacion(
    val id: Int,
    val posicion: String,
    val x: Float,
    val y: Float,
    val jugador: Player? = null
)
