package com.example.trinidad.data.mapper.player

import com.example.trinidad.data.remote.dto.footballdata.PlayerDto
import com.example.trinidad.domain.model.Player
import com.example.trinidad.domain.model.PlayerDetail

object FootballDataPlayerMapper {

    fun map(dto: PlayerDto): Player {
        return Player(
            id = dto.id,
            name = dto.name ?: "Unknown",
            photo = "",
            position = dto.position ?: "Unknown"
        )
    }

    fun mapDetail(dto: PlayerDto): PlayerDetail {
        return PlayerDetail(
            id = dto.id,
            name = dto.name ?: "Unknown",
            photo = "",
            position = dto.position ?: "Unknown",
            height = "Unknown",
            weight = "Unknown",
            age = 0,
            nationality = "Unknown"
        )
    }
}
