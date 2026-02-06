package com.example.trinidad.data.mapper

import com.example.trinidad.data.remote.dto.TeamDetailDto
import com.example.trinidad.domain.model.TeamDetail

fun TeamDetailDto.toDomain(): TeamDetail =
    TeamDetail(
        id = team.id,
        name = team.name,
        code = team.code ?: "N/A",
        country = team.country,
        founded = team.founded?.toString() ?: "Desconocido",
        national = team.national,
        logo = team.logo,

        stadium = venue.name ?: "No disponible",
        stadiumCity = venue.city ?: "No disponible",
        stadiumAddress = venue.address ?: "No disponible",
        capacity = venue.capacity?.toString() ?: "N/A",
        surface = venue.surface ?: "Desconocida",
        stadiumImage = venue.image ?: ""
    )


