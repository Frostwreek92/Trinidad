package com.example.trinidad.data.mapper

import com.example.trinidad.data.remote.dto.TeamDetailDto
import com.example.trinidad.domain.model.TeamDetail

fun TeamDetailDto.toDomain(): TeamDetail =
    TeamDetail(
        id = team.id,
        name = team.name,
        logo = team.logo,
        stadium = venue.name,
        city = venue.city,
        capacity = venue.capacity
    )

