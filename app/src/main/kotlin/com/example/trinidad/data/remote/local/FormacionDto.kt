package com.example.trinidad.data.remote.local

data class FormacionDto(
    val id: Int?,
    val esquema: String,
    val jugadores: List<JugadorEnPosicionDto>
)

data class JugadorEnPosicionDto(
    val jugador: BackendJugadorDto,
    val posicion: String,
    val x: Float,
    val y: Float
)
