package com.example.trinidad.data.mapper.local

import com.example.trinidad.data.remote.local.BackendJugadorDto
import com.example.trinidad.domain.model.api.Player
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackendJugadorMapper @Inject constructor() {

    fun mapToDomain(jugadorDto: BackendJugadorDto): Player {
        return Player(
            id = jugadorDto.idJugador ?: 0,
            name = jugadorDto.nombreJugador,
            position = jugadorDto.posicion,
            photo = jugadorDto.foto.ifEmpty { "https://via.placeholder.com/40" },
            height = "Altura",
            weight = "Peso",
            nationality = "Nacionalidad"
        )
    }

    fun mapToDomain(jugadoresDto: List<BackendJugadorDto>): List<Player> {
        return jugadoresDto.map { mapToDomain(it) }
    }
}