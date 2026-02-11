package com.example.trinidad.data.mapper.team

import com.example.trinidad.data.remote.dto.apifootball.TeamResponseItemDto
import com.example.trinidad.domain.model.Team
import com.example.trinidad.domain.model.TeamDetail

object ApiFootballTeamMapper {

    fun map(item: TeamResponseItemDto): Team {
        return Team(
            id = item.team.id,
            name = item.team.name ?: "",
            logo = item.team.logo ?: ""
        )
    }

    fun mapDetail(item: TeamResponseItemDto): TeamDetail {
        return TeamDetail(
            id = item.team.id,
            name = item.team.name ?: "",
            logo = item.team.logo ?: "",
            stadium = item.venue?.name ?: "",
            city = item.venue?.city ?: "",
            capacity = item.venue?.capacity ?: 0,
            address = item.venue?.address ?: "",
            surface = item.venue?.surface ?: "",
            stadiumImage = item.venue?.image ?: ""
        )
    }
}
