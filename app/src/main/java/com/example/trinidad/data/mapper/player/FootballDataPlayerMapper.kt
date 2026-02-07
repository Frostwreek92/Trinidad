package com.example.trinidad.data.mapper.player

import com.example.trinidad.data.remote.dto.footballdata.PlayerDto
import com.example.trinidad.data.remote.dto.footballdata.SquadPlayerDto
import com.example.trinidad.domain.model.Player
import com.example.trinidad.domain.model.PlayerDetail

object FootballDataPlayerMapper {

    // ─────────────────────────────
    // LISTA DE JUGADORES (SQUAD)
    // ─────────────────────────────
    fun fromSquadItem(dto: PlayerDto): Player {
        return Player(
            id = dto.id,
            name = dto.name,
            position = dto.position ?: "N/A",
            photo = "",
            height = "N/A",
            weight = "N/A",
            nationality = "N/A"
        )
    }

    // ─────────────────────────────
    // DETALLE DE JUGADOR
    // ─────────────────────────────
    fun toDetail(dto: SquadPlayerDto): PlayerDetail {
        return PlayerDetail(
            id = dto.id,
            name = dto.name,
            position = dto.position ?: "N/A",
            age = 0,          // football-data no da edad directa
            nationality = "N/A",
            height = "N/A",
            weight = "N/A",
            photo = ""
        )
    }
}
