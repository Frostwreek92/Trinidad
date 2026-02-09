package com.example.trinidad.data.repository

import com.example.trinidad.data.mapper.team.ApiFootballTeamMapper
import com.example.trinidad.data.mapper.team.FootballDataTeamMapper
import com.example.trinidad.data.remote.ApiProvider
import com.example.trinidad.data.remote.ApiType
import com.example.trinidad.domain.model.Team
import com.example.trinidad.domain.model.TeamDetail
import com.example.trinidad.domain.repository.TeamRepository

class TeamRepositoryImpl(
    private val apiProvider: ApiProvider
) : TeamRepository {

    private val DEFAULT_SEASON = 2023

    override suspend fun getTeamsByLeague(leagueId: Int): List<Team> {
        return when (apiProvider.currentApi) {
            ApiType.API_FOOTBALL -> {
                apiProvider.apiFootball
                    .getTeamsByLeague(leagueId, DEFAULT_SEASON)
                    .response
                    .map { ApiFootballTeamMapper.map(it) }
            }
            ApiType.FOOTBALL_DATA -> {
                apiProvider.footballData
                    .getTeamsByCompetition(leagueId)
                    .teams
                    .map { FootballDataTeamMapper.map(it) }
            }
        }
    }

    override suspend fun getTeamDetail(teamId: Int): TeamDetail {
        return when (apiProvider.currentApi) {
            ApiType.API_FOOTBALL -> {
                val item = apiProvider.apiFootball
                    .getTeamDetail(teamId)
                    .response
                    .first()
                ApiFootballTeamMapper.mapDetail(item)
            }
            ApiType.FOOTBALL_DATA -> {
                val response = apiProvider.footballData.getTeamDetail(teamId)
                FootballDataTeamMapper.mapDetail(response)
            }
        }
    }
}
