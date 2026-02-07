package com.example.trinidad.data.mapper.league

import com.example.trinidad.data.remote.dto.footballdata.CompetitionDto
import com.example.trinidad.domain.model.League

object FootballDataLeagueMapper {

    fun map(dto: CompetitionDto): League {
        return League(
            id = dto.id,
            name = dto.name,
            country = dto.area.name,
            logo = dto.emblem ?: ""
        )
    }
}
