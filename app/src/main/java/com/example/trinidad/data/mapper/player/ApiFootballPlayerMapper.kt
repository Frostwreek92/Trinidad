package com.example.trinidad.data.mapper.player

import com.example.trinidad.data.remote.dto.apifootball.PlayerResponseItemDto
import com.example.trinidad.domain.model.Player
import com.example.trinidad.domain.model.PlayerDetail

object ApiFootballPlayerMapper {

    // ─────────────────────
    // LISTA DE JUGADORES
    // ─────────────────────
    fun fromListItem(dto: PlayerResponseItemDto): Player {
        val stats = dto.statistics.firstOrNull()

        return Player(
            id = dto.player.id,
            name = dto.player.name ?: "Unknown",
            position = stats?.games?.position ?: "N/A",
            photo = dto.player.photo ?: "",
            height = dto.player.height ?: "",
            weight = dto.player.weight ?: "",
            nationality = dto.player.nationality ?: ""
        )
    }

    // ─────────────────────
    // DETALLE DE JUGADOR
    // ─────────────────────
    fun toDetail(dto: PlayerResponseItemDto): PlayerDetail {
        val stats = dto.statistics.firstOrNull()

        return PlayerDetail(
            id = dto.player.id,
            name = dto.player.name ?: "Unknown",
            position = stats?.games?.position ?: "N/A",
            photo = dto.player.photo ?: "",
            height = dto.player.height ?: "N/A",
            weight = dto.player.weight ?: "N/A",
            nationality = dto.player.nationality ?: "Unknown",
            age = dto.player.age ?: 0
        )
    }
}
