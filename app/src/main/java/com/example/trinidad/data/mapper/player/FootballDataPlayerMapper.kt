package com.example.trinidad.data.mapper.player

import com.example.trinidad.data.remote.dto.footballdata.PlayerDto
import com.example.trinidad.data.remote.dto.footballdata.SquadPlayerDto
import com.example.trinidad.domain.model.Player
import com.example.trinidad.domain.model.PlayerDetail

object FootballDataPlayerMapper {

    // Para listas (squad)
    fun fromSquadItem(dto: PlayerDto): Player {
        return Player(
            id = dto.id,
            name = dto.name,
            position = dto.position ?: "N/A",
            photo = "",
            height = TODO(),
            weight = TODO(),
            nationality = TODO() // football-data NO da foto
        )
    }

    // Para detalle de jugador
    fun toDetail(dto: SquadPlayerDto): PlayerDetail {
        return PlayerDetail(
            id = dto.id,
            name = dto.name,
            position = dto.position ?: "N/A",
            age = TODO(),
            nationality = TODO(),
            height = TODO(),
            weight = TODO(),
            photo = TODO()
        )
    }
}
