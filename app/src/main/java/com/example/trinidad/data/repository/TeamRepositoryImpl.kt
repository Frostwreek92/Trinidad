package com.example.trinidad.data.repository

import com.example.trinidad.data.mapper.team.ApiFootballTeamMapper
import com.example.trinidad.data.mapper.team.FootballDataTeamMapper
import com.example.trinidad.data.remote.ApiType
import com.example.trinidad.data.remote.api.ApiFootballApi
import com.example.trinidad.data.remote.api.FootballDataApi
import com.example.trinidad.domain.model.Team
import com.example.trinidad.domain.model.TeamDetail
import com.example.trinidad.domain.repository.TeamRepository

class TeamRepositoryImpl(
    private val apiFootball: ApiFootballApi,
    private val footballData: FootballDataApi,
    private val currentApi: ApiType
) : TeamRepository {

    private val DEFAULT_SEASON = 2023

    override suspend fun getTeamsByLeague(leagueId: Int): List<Team> {
        return when (currentApi) {

            ApiType.API_FOOTBALL -> {
                val response = apiFootball.getTeamsByLeague(
                    leagueId = leagueId,
                    season = DEFAULT_SEASON
                )
                response.response.map {
                    ApiFootballTeamMapper.map(it)
                }
            }

            ApiType.FOOTBALL_DATA -> {
                val response = footballData.getTeamsByCompetition(leagueId)
                response.teams.map {
                    FootballDataTeamMapper.map(it)
                }
            }
        }
    }

    override suspend fun getTeamDetail(teamId: Int): TeamDetail {
        return when (currentApi) {

            ApiType.API_FOOTBALL -> {
                val response = apiFootball.getTeamDetail(teamId)
                val item = response.response.first()
                ApiFootballTeamMapper.mapDetail(item)
            }

            ApiType.FOOTBALL_DATA -> {
                val response = footballData.getTeamDetail(teamId)
                FootballDataTeamMapper.mapDetail(response)
            }
        }
    }

}
