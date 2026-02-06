package com.example.trinidad.data.repository

import androidx.compose.remote.creation.first
import com.example.trinidad.data.mapper.toDomain
import com.example.trinidad.data.remote.api.FootballApi
import com.example.trinidad.domain.model.Team
import com.example.trinidad.domain.model.TeamDetail
import com.example.trinidad.domain.repository.TeamRepository

class TeamRepositoryImpl(
    private val api: FootballApi
) : TeamRepository {

    override suspend fun getTeamsByLeague(leagueId: Int): List<Team> {
        return api.getTeamsByLeague(leagueId)
            .response
            .map { it.toDomain() }
    }

    override suspend fun getTeamDetail(teamId: Int): TeamDetail {
        val result = api.getTeamDetail(teamId)

        val teamDto = result.response.firstOrNull()
            ?: throw Exception("No hay datos del equipo")

        return teamDto.toDomain()
    }


}
