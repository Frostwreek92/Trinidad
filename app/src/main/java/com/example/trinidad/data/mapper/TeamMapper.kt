package com.example.trinidad.data.mapper

import com.example.trinidad.data.remote.dto.TeamDto
import com.example.trinidad.domain.model.Team

fun TeamDto.toDomain(): Team =
    Team(
        id = team.id,
        name = team.name,
        logo = team.logo
    )
