package com.example.trinidad.data.mapper

import com.example.trinidad.data.remote.dto.LeagueDto
import com.example.trinidad.domain.model.League

fun LeagueDto.toDomain(): League =
    League(
        id = league.id,
        name = league.name,
        logo = league.logo
    )