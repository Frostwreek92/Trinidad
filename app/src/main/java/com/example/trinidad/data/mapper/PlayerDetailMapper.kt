package com.example.trinidad.data.mapper

import com.example.trinidad.data.remote.dto.PlayerDto
import com.example.trinidad.domain.model.PlayerDetail

fun PlayerDto.toDetailDomain(): PlayerDetail =
    PlayerDetail(
        id = player.id,
        name = player.name ?: "Jugador desconocido",
        age = player.age ?: 0,
        nationality = player.nationality ?: "N/A",
        position = player.position ?: "Desconocida",
        height = player.height ?: "No disponible",
        weight = player.weight ?: "No disponible",
        photo = player.photo ?: ""
    )