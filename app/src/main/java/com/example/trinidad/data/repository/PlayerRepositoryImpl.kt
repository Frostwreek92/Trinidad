package com.example.trinidad.data.repository

import com.example.trinidad.data.mapper.player.ApiFootballPlayerMapper
import com.example.trinidad.data.mapper.player.FootballDataPlayerMapper
import com.example.trinidad.data.remote.ApiProvider
import com.example.trinidad.data.remote.ApiType
import com.example.trinidad.domain.model.Player
import com.example.trinidad.domain.model.PlayerDetail
import com.example.trinidad.domain.repository.PlayerRepository

class PlayerRepositoryImpl(
    private val apiProvider: ApiProvider
) : PlayerRepository {

    override suspend fun getPlayersByTeam(teamId: Int): List<Player> {
        return when (apiProvider.apiType) {

            ApiType.API_FOOTBALL -> {
                val response = apiProvider.apiFootballApi
                    .getPlayersByTeam(teamId = teamId, season = 2023)

                response.response.map {
                    ApiFootballPlayerMapper.map(it.player)
                }
            }

            ApiType.FOOTBALL_DATA -> {
                val response = apiProvider.footballDataApi
                    .getTeamSquad(teamId)

                response.squad.map {
                    FootballDataPlayerMapper.map(it)
                }
            }
        }
    }

    override suspend fun getPlayerDetail(playerId: Int): PlayerDetail {
        return when (apiProvider.apiType) {

            ApiType.API_FOOTBALL -> {
                val response = apiProvider.apiFootballApi
                    .getPlayerDetail(playerId = playerId, season = 2023)

                ApiFootballPlayerMapper.mapDetail(
                    response.response.first().player
                )
            }

            ApiType.FOOTBALL_DATA -> {
                // football-data.org NO tiene endpoint de detalle de jugador
                // reutilizamos el mapper básico
                PlayerDetail(
                    id = playerId,
                    name = "Unknown",
                    photo = "",
                    position = "Unknown",
                    height = "Unknown",
                    weight = "Unknown",
                    age = 0,
                    nationality = "Unknown"
                )
            }
        }
    }
}
