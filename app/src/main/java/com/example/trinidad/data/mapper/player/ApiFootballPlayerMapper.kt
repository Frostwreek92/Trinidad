package com.example.trinidad.data.mapper.player

import com.example.trinidad.data.remote.dto.apifootball.PlayerDto
import com.example.trinidad.domain.model.Player
import com.example.trinidad.domain.model.PlayerDetail

object ApiFootballPlayerMapper {

    fun map(dto: PlayerDto): Player {
        return Player(
            id = dto.id,
            name = dto.name ?: "Unknown",
            photo = dto.photo ?: "",
            position = "Unknown"
        )
    }

    fun mapDetail(dto: PlayerDto): PlayerDetail {
        return PlayerDetail(
            id = dto.id,
            name = dto.name ?: "Unknown",
            photo = dto.photo ?: "",
            position = "Unknown",
            height = "Unknown",
            weight = "Unknown",
            age = 0,
            nationality = "Unknown"
        )
    }
}
