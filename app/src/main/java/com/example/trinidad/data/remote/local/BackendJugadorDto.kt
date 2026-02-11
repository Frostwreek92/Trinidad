package com.example.trinidad.data.remote.local

data class BackendJugadorDto(
    val idJugador: Int?,
    val nombreJugador: String,
    val idEquipo: Int?,
    val posicion: String,
    val edad: Int,
    val dorsal: Int,
    val foto: String,
    val nombreEquipo: String?
)