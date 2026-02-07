package com.example.trinidad.data.repository

import com.example.trinidad.data.mapper.league.ApiFootballLeagueMapper
import com.example.trinidad.data.mapper.league.FootballDataLeagueMapper
import com.example.trinidad.data.remote.ApiProvider
import com.example.trinidad.data.remote.ApiType
import com.example.trinidad.domain.model.League
import com.example.trinidad.domain.repository.LeagueRepository

class LeagueRepositoryImpl(
    private val apiProvider: ApiProvider
) : LeagueRepository {

    override suspend fun getLeagues(): List<League> {
        return when (apiProvider.currentApi) {

            ApiType.API_FOOTBALL -> {
                apiProvider.apiFootball
                    .getLeagues()
                    .response
                    .map { ApiFootballLeagueMapper.map(it) }
            }

            ApiType.FOOTBALL_DATA -> {
                apiProvider.footballData
                    .getCompetitions()
                    .competitions
                    .map { FootballDataLeagueMapper.map(it) }
            }
        }
    }
}
