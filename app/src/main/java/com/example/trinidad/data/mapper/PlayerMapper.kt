package com.example.trinidad.data.mapper

import com.example.trinidad.data.remote.dto.PlayerDto
import com.example.trinidad.domain.model.Player

fun PlayerDto.toDomain(): Player =
    Player(
        id = player.id,
        name = player.name ?: "Jugador sin nombre",
        position = player.position ?: "Desconocida",
        photo = player.photo ?: ""
    )
