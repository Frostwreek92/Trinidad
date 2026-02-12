package com.example.trinidad.data.mapper.league

import com.example.trinidad.data.remote.dto.apifootball.LeagueDto
import com.example.trinidad.domain.model.api.League

object ApiFootballLeagueMapper {

    fun map(dto: LeagueDto): League {
        return League(
            id = dto.league.id,
            name = dto.league.name,
            country = dto.country?.name ?: "",
            logo = dto.league.logo ?: ""
        )
    }
}
