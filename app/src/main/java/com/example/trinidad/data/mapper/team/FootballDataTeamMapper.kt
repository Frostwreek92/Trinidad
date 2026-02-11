package com.example.trinidad.data.mapper.team

import com.example.trinidad.data.remote.dto.footballdata.TeamDto
import com.example.trinidad.domain.model.Team
import com.example.trinidad.domain.model.TeamDetail

object FootballDataTeamMapper {

    fun map(team: TeamDto): Team =
        Team(
            id = team.id,
            name = team.name ?: "Desconocido",
            logo = team.crest ?: ""
        )

    fun mapDetail(team: TeamDto): TeamDetail =
        TeamDetail(
            id = team.id,
            name = team.name ?: "Desconocido",
            logo = team.crest ?: "",
            stadium = team.venue ?: "Desconocido",
            city = team.area?.name ?: "Desconocido",
            capacity = 0,
            address = "",
            surface = "",
            stadiumImage = ""
        )
}
