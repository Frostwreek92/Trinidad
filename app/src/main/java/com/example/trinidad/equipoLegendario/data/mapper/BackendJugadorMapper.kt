package com.example.trinidad.equipoLegendario.data.mapper

import com.example.trinidad.equipoLegendario.data.remote.dto.BackendJugadorDto
import com.example.trinidad.domain.model.Player
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackendJugadorMapper @Inject constructor() {

    fun mapToDomain(jugadorDto: BackendJugadorDto): Player {
        return Player(
            id = jugadorDto.idJugador ?: 0,
            name = jugadorDto.nombreJugador,
            position = jugadorDto.posicion,
            photo = jugadorDto.foto.ifEmpty { "https://via.placeholder.com/40" }
        )
    }

    fun mapToDomain(jugadoresDto: List<BackendJugadorDto>): List<Player> {
        return jugadoresDto.map { mapToDomain(it) }
    }
}